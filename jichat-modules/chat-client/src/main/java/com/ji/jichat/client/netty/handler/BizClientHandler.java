package com.ji.jichat.client.netty.handler;


import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.client.client.ClientInfo;
import com.ji.jichat.client.dto.AppUpMessage;
import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.util.GuardedObject;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
//@ChannelHandler.Sharable 用于标识一个可以在多个 Channel 上共享使用的 ChannelHandler
@ChannelHandler.Sharable
@Qualifier("bizClientHandler")
public class BizClientHandler extends SimpleChannelInboundHandler<DownMessage> {

    public static final String NAME = "bizClientHandler";


    @Resource
    private ClientInfo clientInfo;


    @Override
    public void channelRead0(ChannelHandlerContext ctx, DownMessage message) {
        log.info("收到服务端消息:{}", message);
        GuardedObject.fireEvent(message.getNonce(), message);
        if (message.getCode().equals(CommandCodeEnum.PRIVATE_MESSAGE.getCode())) {
            clientInfo.getUserId();
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        长时间，没有发消息给服务端，开始发送心跳
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                sendPing(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    private void sendPing(ChannelHandlerContext ctx) {
        final AppUpMessage appUpMessage = new AppUpMessage(clientInfo);
        appUpMessage.setCode(CommandCodeEnum.HEARTBEAT.getCode());
        appUpMessage.setContent("ping");
        log.info("发送给服务端心跳:{},服务端:{}",appUpMessage.getContent(),clientInfo.getUserChatServerVO().getHttpAddress());
        ctx.writeAndFlush(appUpMessage).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                log.error("IO error,close Channel");
                future.channel().close();
            }
        });
    }

}
