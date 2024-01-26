package com.ji.jichat.client.netty;


import com.ji.jichat.client.netty.handler.BizClientHandler;
import com.ji.jichat.client.netty.handler.ClentLengthFieldDecoderHandler;
import com.ji.jichat.client.netty.handler.ClentPacketCodecHandler;
import com.ji.jichat.client.netty.handler.ConnectionTimeHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Resource
    private BizClientHandler bizClientHandler;


    @Override
    protected void initChannel(SocketChannel channel) {

        channel.pipeline()
                //10 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(0, 10, 0))
                .addLast(new ConnectionTimeHandler())
//                // MDC日志上下文
//                .addLast(MdcLoggingHandler.NAME, mdcLoggingHandler)
                //自定义长度解码器
                .addLast(ClentLengthFieldDecoderHandler.NAME, new ClentLengthFieldDecoderHandler())
                // 编解码
                .addLast(ClentPacketCodecHandler.NAME, new ClentPacketCodecHandler())
                // 业务处理器
                .addLast(BizClientHandler.NAME, bizClientHandler)
        ;


    }

}
