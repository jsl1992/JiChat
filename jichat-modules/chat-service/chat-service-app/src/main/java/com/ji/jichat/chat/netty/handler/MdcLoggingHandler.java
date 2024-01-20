package com.ji.jichat.chat.netty.handler;


import com.ji.jichat.web.core.constant.TraceSpanContext;
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
        TraceSpanContext.storeTraceSpan();
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.fireChannelReadComplete();
        TraceSpanContext.removeTraceSpan();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.fireExceptionCaught(cause);
        TraceSpanContext.removeTraceSpan();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        TraceSpanContext.removeTraceSpan();
    }
}
