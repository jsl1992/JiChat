package com.ji.jichat.client.netty.handler;

import com.ji.jichat.client.netty.protocol.ClentProtocolCodec;
import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.pojo.UpMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *
 */
@Slf4j
@ChannelHandler.Sharable
public class ClentPacketCodecHandler extends MessageToMessageCodec<ByteBuf, Object> {

    public static final String NAME = "PacketCodecHandler";




    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
        if (msg instanceof UpMessage) {
            ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
            UpMessage upMessage = (UpMessage) msg;
            ClentProtocolCodec.encode(byteBuf, upMessage);
            out.add(byteBuf);
        } else {
            log.error("msg 必须为 UpMessage 类型");
            throw new IllegalArgumentException("msg 必须为UpMessage 类型");
        }

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        final DownMessage downMessage = ClentProtocolCodec.decode(byteBuf);
        downMessage.setClientIp(ClentProtocolCodec.getClientIp(ctx));
        out.add(downMessage);
    }


}
