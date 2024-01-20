package com.ji.jichat.chat.netty;


import com.ji.jichat.chat.core.config.TcpServerConfig;
import com.ji.jichat.chat.netty.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Qualifier("serverChannelInitializer")
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

//    @Resource
//    private TcpServerConfig tcpServerConfig;

    @Resource
    @Qualifier("bizServerHandler")
    private BizServerHandler bizServerHandler;


    @Resource
    private MdcLoggingHandler mdcLoggingHandler;

    @Resource
    private PacketCodecHandler packetCodecHandler;

    @Resource
    private LoginHandler loginHandler;


    @Override
    protected void initChannel(SocketChannel channel) {

        channel.pipeline()
//            统计连接时间
                .addLast(new ConnectionTimeHandler())
                // MDC日志上下文
                .addLast(MdcLoggingHandler.NAME, mdcLoggingHandler)
                //自定义长度解码器
                .addLast(LengthFieldDecoderHandler.NAME, new LengthFieldDecoderHandler())
                // 编解码
                .addLast(PacketCodecHandler.NAME, packetCodecHandler)
                // 心跳处理器
//            .addLast(HeartBeatServerHandler.NAME, new HeartBeatServerHandler())
//                登录处理器
                .addLast(loginHandler)
                // 业务处理器
                .addLast(BizServerHandler.NAME, bizServerHandler);
        // 连接异常处理

    }

}
