package com.ji.jichat.chat.netty.listener;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Netty 关闭事件
 */
@Slf4j
@Component
public class NettyCloseFutureListener implements ChannelFutureListener {


    @Override
    public void operationComplete(ChannelFuture future) {
//        关闭，到时候添加一些。关闭需要处理的事情
        if (future.isSuccess()) {
            log.info("Netty 成功降落");
        } else {
            log.error("Netty 坠毁!!!!");
        }

    }


}
