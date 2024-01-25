package com.ji.jichat.client.netty.handler;


import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.client.client.ClientInfo;
import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.util.GuardedObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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

}
