package com.ji.jichat.chat.strategy.receive;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ji.jichat.chat.api.dto.ChatMessageDTO;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.api.enums.DeviceTypeEnum;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.manager.MessageIdGenerate;
import com.ji.jichat.chat.mq.producer.MessageProducer;
import com.ji.jichat.chat.strategy.CommandStrategy;
import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.common.enums.CommonStatusEnum;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.common.pojo.UpMessage;
import com.ji.jichat.user.api.DeviceRpc;
import com.ji.jichat.user.api.vo.DeviceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 指令回复Processor
 *
 * @author jishenglong on 2023/3/27 10:02
 **/
@Component
@Slf4j
public class MessageProcessor implements CommandStrategy {

    @Resource
    private MessageIdGenerate messageIdGenerate;

    @Resource
    private DeviceRpc deviceRpc;

    @Resource
    private MessageProducer messageProducer;

    @Resource
    private RedisTemplate<String, UserChatServerVO> redisTemplate;

    @Override
    public CommandCodeEnum getCommandCode() {
        return CommandCodeEnum.MESSAGE;
    }

    @Override
    public String execute(UpMessage message) {
        final ChatMessageDTO chatMessageDTO = JSON.parseObject(message.getContent(), ChatMessageDTO.class);
        final long messageId = messageIdGenerate.genMessageId(chatMessageDTO.getMessageFrom(), chatMessageDTO.getMessageTo());
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageId", messageId);
        //通过mq转发消息，给消息接收者
        final CommonResult<List<DeviceVO>> onlineDevicesResult = deviceRpc.getOnlineDevices(chatMessageDTO.getMessageTo());
        onlineDevicesResult.checkError();
        final List<DeviceVO> toOnlineDevices = onlineDevicesResult.getData();
        for (DeviceVO deviceVO : toOnlineDevices) {
            if (Objects.equals(deviceVO.getDeviceStatus(), CommonStatusEnum.ENABLE.getStatus())) {
                final UserChatServerVO userChatServerVO = redisTemplate.opsForValue().get(CacheConstant.LOGIN_USER_CHAT_SERVER + deviceVO.getUserId() + "_" + deviceVO.getDeviceType());
                //在线发送消息
                messageProducer.sendMessage(userChatServerVO.getHttpAddress(), JSON.toJSONString(chatMessageDTO));
            } else if (Objects.equals(deviceVO.getDeviceType(), DeviceTypeEnum.MOBILE.getCode())) {
                //手机登录那么发送 消息推送到客户端
            }
        }
        // 和同一个用户的其他登录设备
//      将mq消息入库
        return jsonObject.toJSONString();
    }
}
