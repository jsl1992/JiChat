package com.ji.jichat.chat.netty.handler;

import cn.hutool.core.date.DateUtil;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.api.enums.MessageTypeEnum;
import com.ji.jichat.chat.convert.MessageConvert;
import com.ji.jichat.chat.kit.UserChatServerCache;
import com.ji.jichat.chat.netty.ChannelRepository;
import com.ji.jichat.chat.utils.NettyAttrUtil;
import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.pojo.UpMessage;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartbeatHandler extends SimpleChannelInboundHandler<UpMessage> {

    @Resource
    private UserChatServerCache userChatServerCache;

    private long connectionStartTime;

    private long connectionCount;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        connectionCount += 1;
        log.info("客户端={}连接成功,连接个数:{}", ctx.channel().remoteAddress(), connectionCount);
        connectionStartTime = System.currentTimeMillis();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, UpMessage msg) {
        if (!msg.isMatch(CommandCodeEnum.HEARTBEAT.getCode())) {
            ctx.fireChannelRead(msg);
            return;
        }
//        收到客户端的心跳消息
        log.debug("收到客户端心跳:{}",msg);
        NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
        final DownMessage downMessage = MessageConvert.INSTANCE.convert(msg);
        downMessage.setType(MessageTypeEnum.DOWN.getCode());
        downMessage.setContent("pong");
        ctx.writeAndFlush(downMessage).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                log.error("IO error,close Channel");
                future.channel().close();
            }
        });
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                //长时间没有收到客户端消息，开始 处理心跳
                final Channel channel = ctx.channel();
                final Long lastReadTime = NettyAttrUtil.getReaderTime(channel);
                final long now = System.currentTimeMillis();
                if (TimeUnit.MILLISECONDS.toMinutes(now - lastReadTime) > 1) {
                    log.info("长时间没有收到【{}】心跳，关闭客户端连接", ChannelRepository.getUserKey(channel));
//                    超过一分钟，那么就关闭连接
                    userChatServerCache.remove(channel);
                    channel.close();
                }
            }
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        userChatServerCache.remove(ctx.channel());
        connectionCount -= 1;
        long connectionEndTime = System.currentTimeMillis();
        long connectionDuration = connectionEndTime - connectionStartTime;
        log.info("客户端={}断开连接,连接开始时间={}，连接时长:{}分钟", ctx.channel().remoteAddress(), DateUtil.date(connectionStartTime), TimeUnit.MILLISECONDS.toMinutes(connectionDuration));
    }
}
