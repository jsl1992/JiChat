package com.ji.jichat.chat.netty.handler;

import com.ji.jichat.chat.netty.protocol.ProtocolCodec;
import com.ji.jichat.chat.api.dto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Object> {

    public static final String NAME = "PacketCodecHandler";


    private PacketCodecHandler() {

    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
        if (msg instanceof Message) {
            ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
            Message message = (Message) msg;
            ProtocolCodec.encode(byteBuf, message);
            out.add(byteBuf);
        } else {
            log.error("msg 必须为Message类型");
            throw new IllegalArgumentException("msg 必须为Message类型");
        }

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        final Message message = ProtocolCodec.decode(byteBuf);
        out.add(message);
    }


}
