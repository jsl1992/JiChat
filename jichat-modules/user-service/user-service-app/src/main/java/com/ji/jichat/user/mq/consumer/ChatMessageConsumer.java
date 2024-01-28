package com.ji.jichat.user.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.ji.jichat.chat.api.dto.ChatMessageSendDTO;
import com.ji.jichat.common.constants.RabbitMQConstants;
import com.ji.jichat.user.service.IChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ChatMessageConsumer {

    @Resource
    private IChatMessageService chatMessageService;


    @RabbitListener(queues = RabbitMQConstants.QUEUE_CHAT_MSG_STORE)
    public void receiveMessage(String message) {
        System.out.println("Message received QUEUE_CHAT_MSG_STORE: " + message);
        final ChatMessageSendDTO chatMessageSendDTO = JSON.parseObject(message, ChatMessageSendDTO.class);
        chatMessageService.saveMessage(chatMessageSendDTO);
    }
}
