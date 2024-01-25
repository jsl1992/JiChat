package com.ji.jichat.client.netty.handler;

import cn.hutool.core.date.DateUtil;

import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.client.dto.AppUpMessage;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ClientHeartbeatHandler extends ChannelInboundHandlerAdapter {

    private long connectionStartTime;


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("客户端连接服务端成功");
        connectionStartTime = System.currentTimeMillis();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        long connectionEndTime = System.currentTimeMillis();
        long connectionDuration = connectionEndTime - connectionStartTime;
        log.info("客户端={}断开连接,连接开始时间={}，连接时长:{}分钟", ctx.channel().remoteAddress(), DateUtil.date(connectionStartTime), TimeUnit.MILLISECONDS.toMinutes(connectionDuration));

    }


}
