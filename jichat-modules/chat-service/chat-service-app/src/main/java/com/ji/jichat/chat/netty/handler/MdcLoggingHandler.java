package com.ji.jichat.chat.netty.handler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class MdcLoggingHandler extends ChannelInboundHandlerAdapter {

    public static final String NAME = "MdcLoggingHandler";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        SpanContext.storeSpan();
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
//        SpanContext.cleanSpan();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
//        SpanContext.cleanSpan();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
//        SpanContext.cleanSpan();
    }
}
