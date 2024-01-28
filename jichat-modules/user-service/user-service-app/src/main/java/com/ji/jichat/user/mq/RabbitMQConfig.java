package com.ji.jichat.user.mq;

import com.ji.jichat.common.constants.RabbitMQConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//当没有这个队列的时候会自动创建
@Configuration
public class RabbitMQConfig {

    @Bean
    Queue queueChatMsgStore() {
        String queueName = RabbitMQConstants.QUEUE_CHAT_MSG_STORE;
        Queue queue = new Queue(queueName, true);
        return queue;
    }
}