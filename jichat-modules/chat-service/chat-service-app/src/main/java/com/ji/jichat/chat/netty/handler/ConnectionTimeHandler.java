package com.ji.jichat.chat.netty.handler;

import cn.hutool.core.date.DateUtil;

import com.ji.jichat.chat.netty.ChannelRepository;
import com.ji.jichat.chat.netty.protocol.ProtocolCodec;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ConnectionTimeHandler extends ChannelInboundHandlerAdapter {

    private long connectionStartTime;

    private long connectionCount;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        connectionCount += 1;
        final String clientIp = ProtocolCodec.getClientIp(ctx);
        ChannelRepository.put(clientIp, ctx.channel());
        log.info("客户端={}连接成功,连接个数:{}", ctx.channel().remoteAddress(), connectionCount);
        connectionStartTime = System.currentTimeMillis();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        connectionCount -= 1;
        long connectionEndTime = System.currentTimeMillis();
        long connectionDuration = connectionEndTime - connectionStartTime;
        final String clientIp = ProtocolCodec.getClientIp(ctx);
        ChannelRepository.remove(clientIp, ctx.channel());
        log.info("客户端={}断开连接,连接开始时间={}，连接时长:{}分钟", ctx.channel().remoteAddress(), DateUtil.date(connectionStartTime), TimeUnit.MILLISECONDS.toMinutes(connectionDuration));
    }
}
