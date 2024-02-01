package com.ji.jichat.chat.service;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.ji.jichat.chat.api.dto.ChatSendMessage;
import com.ji.jichat.chat.api.dto.ChatSendReturnMessage;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.api.enums.DeviceTypeEnum;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.chat.convert.MessageConvert;
import com.ji.jichat.chat.kit.UserChatServerCache;
import com.ji.jichat.chat.manager.MessageIdGenerate;
import com.ji.jichat.chat.mq.producer.ChatMessageProducer;
import com.ji.jichat.user.api.DeviceRpc;
import com.ji.jichat.user.api.vo.DeviceVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 指令回复Processor
 *
 * @author jisl on 2023/3/27 10:02
 **/
@Component
@Slf4j
public class ChatMessageService {

    @Resource
    private MessageIdGenerate messageIdGenerate;

    @Resource
    private DeviceRpc deviceRpc;

    @Resource
    private ChatMessageProducer chatMessageProducer;

    @Resource
    private UserChatServerCache userChatServerCache;


    public ChatSendReturnMessage processMessage(ChatSendMessage message) {
        //todo  通过channelKey 校验是否好友关系，没有好友关系。不让发送
        final Long messageId = messageIdGenerate.genMessageId(message.getMessageFrom(), message.getMessageTo());
        message.setMessageId(messageId);
//        消息时间以服务器时间为准，以防不同客户端时间相差太多
        message.setCreateTime(new Date());

        //通过mq转发消息，给消息接收者
        forwardMessage(message);
        // 和同一个用户的其他登录设备
        syncOtherDevicesMessage(message);
//      将mq消息入库
        chatMessageProducer.storeChatMessage(message);
        final ChatSendReturnMessage chatSendReturnMessage = MessageConvert.INSTANCE.convert(message);
        return chatSendReturnMessage;
    }

    private void syncOtherDevicesMessage(ChatSendMessage chatMessageDTO) {
        final List<DeviceVO> fromOnlineDevices = deviceRpc.getOnlineDevices(chatMessageDTO.getMessageFrom()).getCheckedData();
        for (DeviceVO deviceVO : fromOnlineDevices) {
            final String userKey = userChatServerCache.getUserKey(deviceVO.getUserId(), deviceVO.getDeviceType());
            if (Objects.equals(userKey, chatMessageDTO.getUserKey())) {
                //同一个设备，那么不用发给自己。直接跳过
                continue;
            }
            if (userChatServerCache.hasKey(userKey)) {
                sendChatMsgToClient(chatMessageDTO, deviceVO);
            }
        }
    }

    private void sendChatMsgToClient(ChatSendMessage message, DeviceVO deviceVO) {
        final String userKey = userChatServerCache.getUserKey(deviceVO.getUserId(), deviceVO.getDeviceType());
        final ChatSendMessage receiveMessage = BeanUtil.toBean(message, ChatSendMessage.class);
        receiveMessage.setUserKey(userKey);
        receiveMessage.setCode(CommandCodeEnum.PRIVATE_MESSAGE_RECEIVE.getCode());
        receiveMessage.setNonce(RandomStringUtils.randomAlphanumeric(16));
        final UserChatServerVO userChatServerVO = userChatServerCache.get(userKey);
        //在线发送消息
        chatMessageProducer.sendMessage(JSON.toJSONString(receiveMessage), userChatServerVO.getHttpAddress());
    }

    private void forwardMessage(ChatSendMessage chatMessageDTO) {
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
