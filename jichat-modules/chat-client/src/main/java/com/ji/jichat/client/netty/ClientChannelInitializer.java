package com.ji.jichat.client.netty;


import com.ji.jichat.client.netty.handler.BizClientHandler;
import com.ji.jichat.client.netty.handler.ClentConnectionTimeHandler;
import com.ji.jichat.client.netty.handler.ClentLengthFieldDecoderHandler;
import com.ji.jichat.client.netty.handler.ClentPacketCodecHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Resource
    private BizClientHandler bizClientHandler;


    @Override
    protected void initChannel(SocketChannel channel) {

        channel.pipeline()
//            统计连接时间
                .addLast(new ClentConnectionTimeHandler())
//                // MDC日志上下文
//                .addLast(MdcLoggingHandler.NAME, mdcLoggingHandler)
                //自定义长度解码器
                .addLast(ClentLengthFieldDecoderHandler.NAME, new ClentLengthFieldDecoderHandler())
                // 编解码
                .addLast(ClentPacketCodecHandler.NAME, new ClentPacketCodecHandler())
                // 心跳处理器
//            .addLast(HeartBeatServerHandler.NAME, new HeartBeatServerHandler())
                // 业务处理器
                .addLast(BizClientHandler.NAME, bizClientHandler)
        ;

        // 连接异常处理

    }

}
