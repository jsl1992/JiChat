package com.ji.jichat.chat.mq;

import framework.constants.RabbitMQConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//当没有这个队列的时候会自动创建
@Configuration
public class RabbitMQConfig {
    @Value("${inner-ip}")
    private String innerIp;

    @Value("${server.port}")
    private String serverPort;

    @Bean
    Queue queueChatMsgNetty() {
        String queueName = RabbitMQConstants.QUEUE_CHAT_MSG_NETTY + innerIp + "_" + serverPort;
        Queue queue = new Queue(queueName, true);
        return queue;
    }
}