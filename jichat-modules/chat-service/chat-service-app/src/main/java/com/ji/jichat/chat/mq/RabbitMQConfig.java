package com.ji.jichat.chat.mq;

import com.ji.jichat.common.constants.RabbitMQConstants;
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
        //当当前服务的ip和端口创建，这样避免mq发消息转发到错误的chat服务器上
        String queueName = RabbitMQConstants.QUEUE_CHAT_MSG_NETTY + innerIp + ":" + serverPort;
        return new Queue(queueName, true);
    }

    @Bean
    Queue queueChatMsgStore() {
        String queueName = RabbitMQConstants.QUEUE_CHAT_MSG_STORE;
        return new Queue(queueName, true);
    }
}