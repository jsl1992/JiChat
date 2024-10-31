package com.ji.jichat.client.netty.listener;

import com.ji.jichat.client.client.JiChatClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * Netty 关闭事件
 */
@Slf4j
@Component
public class NettyCloseFutureListener implements ChannelFutureListener {

    @Resource
    private JiChatClient jiChatClient;

    @Override
    public void operationComplete(ChannelFuture future) {
//        关闭，到时候添加一些。关闭需要处理的事情
        jiChatClient.close();
        if (future.isSuccess()) {
            log.info("Netty 成功降落");
        } else {
            log.error("Netty 坠毁!!!!");
        }

    }


}
