package com.ji.jichat.chat.netty.listener;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Netty 启动成功
 */
@Slf4j
@Component
public class NettyStartFutureListener implements ChannelFutureListener {

    @Override
    public void operationComplete(ChannelFuture future) {
        log.info("Netty 成功起飞");
        //性能监控与统计,有空添加
    }


}
