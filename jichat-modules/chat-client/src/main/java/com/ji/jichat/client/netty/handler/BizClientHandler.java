package com.ji.jichat.client.netty.handler;


import com.ji.jichat.common.pojo.DownMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
//@Component
@Qualifier("BizClientHandler")
//@ChannelHandler.Sharable 用于标识一个可以在多个 Channel 上共享使用的 ChannelHandler
@ChannelHandler.Sharable
public class BizClientHandler extends SimpleChannelInboundHandler<DownMessage> {

    public static final String NAME = "BizClientHandler";

    // 所有消息数量
    private final AtomicLong allMsgCount = new AtomicLong(0);


    @Override
    public void channelRead0(ChannelHandlerContext ctx, DownMessage message) {
        log.info("收到服务端消息:{}", message);
    }

}
