package com.ji.jichat.chat.mq.consumer;

import framework.constants.RabbitMQConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConstants.QUEUE_CHAT_MSG_NETTY+"#{environment.getProperty('inner-ip')}_#{environment.getProperty('server.port')}")
    public void receiveMessage(String message) {
        System.out.println("Message received: " + message);
    }
}
