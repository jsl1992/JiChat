package com.ji.jichat.chat.mq.producer;

import com.ji.jichat.common.constants.RabbitMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage( String message,String target) {
        final String queue = RabbitMQConstants.QUEUE_CHAT_MSG_NETTY + target;
        amqpTemplate.convertAndSend(queue, message);
        log.info("MQ sent:{},message:{} ", queue, message);
    }
}
