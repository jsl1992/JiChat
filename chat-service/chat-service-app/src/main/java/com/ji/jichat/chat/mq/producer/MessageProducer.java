package com.ji.jichat.chat.mq.producer;

import com.ji.jichat.common.constants.RabbitMQConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String message) {
        amqpTemplate.convertAndSend(RabbitMQConstants.QUEUE_CHAT_MSG_NETTY+"192.168.77.130_18080", message);
        System.out.println("Message sent: " + message);
    }
}
