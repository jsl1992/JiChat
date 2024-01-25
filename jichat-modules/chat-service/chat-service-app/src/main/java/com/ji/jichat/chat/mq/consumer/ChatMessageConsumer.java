package com.ji.jichat.chat.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.ji.jichat.chat.kit.UserChatServerCache;
import com.ji.jichat.chat.netty.ChannelRepository;
import com.ji.jichat.common.constants.RabbitMQConstants;
import com.ji.jichat.common.pojo.DownMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
@Slf4j
public class ChatMessageConsumer {

    @Resource
    private UserChatServerCache userChatServerCache;


    @RabbitListener(queues = RabbitMQConstants.QUEUE_CHAT_MSG_NETTY + "#{environment.getProperty('inner-ip')}:#{environment.getProperty('server.port')}")
    public void receiveMessage(String message) {
        System.out.println("Message received: " + message);
        final DownMessage downMessage = JSON.parseObject(message, DownMessage.class);
        final Channel channel = ChannelRepository.get(userChatServerCache.getKey(downMessage.getUserId(), downMessage.getDeviceType()));
        if (Objects.isNull(channel)) {
            log.info("连接客户端连接关闭了，下发消息到客户端失败:{}", message);
            return;
        }
        final ChannelFuture channelFuture = channel.writeAndFlush(downMessage);
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                log.info("转发接收消息到客户端成功:{}", downMessage.getNonce());
            } else {
                log.warn("转发接收消息到客户端失败:{}", downMessage.getNonce());
            }
        });


    }
}
