package com.ji.jichat.chat.strategy.receive;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ji.jichat.chat.api.dto.ChatMessageDTO;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.api.enums.DeviceTypeEnum;
import com.ji.jichat.chat.api.enums.MessageTypeEnum;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.kit.UserChatServerCache;
import com.ji.jichat.chat.manager.MessageIdGenerate;
import com.ji.jichat.chat.mq.producer.ChatMessageProducer;
import com.ji.jichat.chat.strategy.CommandStrategy;
import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.pojo.UpMessage;
import com.ji.jichat.user.api.DeviceRpc;
import com.ji.jichat.user.api.vo.DeviceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 指令回复Processor
 *
 * @author jishenglong on 2023/3/27 10:02
 **/
@Component
@Slf4j
public class ChatMessageProcessor implements CommandStrategy {

    @Resource
    private MessageIdGenerate messageIdGenerate;

    @Resource
    private DeviceRpc deviceRpc;

    @Resource
    private ChatMessageProducer chatMessageProducer;

    @Resource
    private UserChatServerCache userChatServerCache;

    @Override
    public CommandCodeEnum getCommandCode() {
        return CommandCodeEnum.PRIVATE_MESSAGE;
    }

    @Override
    public String execute(UpMessage message) {
        final ChatMessageDTO chatMessageDTO = JSON.parseObject(message.getContent(), ChatMessageDTO.class);
        final long messageId = messageIdGenerate.genMessageId(chatMessageDTO.getMessageFrom(), chatMessageDTO.getMessageTo());
        chatMessageDTO.setMessageId(messageId);
//        消息时间以服务器时间为准，以防不同客户端时间相差太多
        chatMessageDTO.setCreateTime(new Date());

        //通过mq转发消息，给消息接收者
        forwardMessage(chatMessageDTO);
        // 和同一个用户的其他登录设备
        syncOtherDevicesMessage(chatMessageDTO, message);
//      将mq消息入库
        chatMessageProducer.storeChatMessage(chatMessageDTO);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageId", messageId);
        return jsonObject.toJSONString();
    }

    private void syncOtherDevicesMessage(ChatMessageDTO chatMessageDTO, UpMessage message) {
        final List<DeviceVO> fromOnlineDevices = deviceRpc.getOnlineDevices(chatMessageDTO.getMessageFrom()).getCheckedData();
        for (DeviceVO deviceVO : fromOnlineDevices) {
            final String userKey = userChatServerCache.getUserKey(deviceVO.getUserId(), deviceVO.getDeviceType());
            if (Objects.equals(userKey, message.getUserKey())) {
                //同一个设备，那么不用发给自己。直接跳过
                continue;
            }
            if (userChatServerCache.hasKey(userKey)) {
                sendChatMsgToClient(chatMessageDTO, deviceVO);
            }
        }
    }

    private void sendChatMsgToClient(ChatMessageDTO chatMessageDTO, DeviceVO deviceVO) {
        final String userKey = userChatServerCache.getUserKey(deviceVO.getUserId(), deviceVO.getDeviceType());
        final DownMessage downMessage = DownMessage.builder()
                .userKey(userKey).code(CommandCodeEnum.PRIVATE_MESSAGE_RECEIVE.getCode())
                .content(JSON.toJSONString(chatMessageDTO)).nonce(IdUtil.objectId()).type(MessageTypeEnum.DOWN.getCode())
                .build();
        final UserChatServerVO userChatServerVO = userChatServerCache.get(userKey);
        //在线发送消息
        chatMessageProducer.sendMessage(JSON.toJSONString(downMessage), userChatServerVO.getHttpAddress());
    }

    private void forwardMessage(ChatMessageDTO chatMessageDTO) {
        final List<DeviceVO> toOnlineDevices = deviceRpc.getOnlineDevices(chatMessageDTO.getMessageTo()).getCheckedData();
        for (DeviceVO deviceVO : toOnlineDevices) {
            if (userChatServerCache.hasKey(deviceVO.getUserId(), deviceVO.getDeviceType())) {
                sendChatMsgToClient(chatMessageDTO, deviceVO);
            } else if (Objects.equals(deviceVO.getDeviceType(), DeviceTypeEnum.MOBILE.getCode())) {
                log.info("手机登录当前设备没在线，使用苹果或者安卓推送，推送到手机");
            }
        }
    }
}
