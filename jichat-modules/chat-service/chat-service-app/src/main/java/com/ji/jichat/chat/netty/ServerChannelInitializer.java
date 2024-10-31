package com.ji.jichat.chat.netty;


import com.ji.jichat.chat.netty.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


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
    private HeartbeatHandler heartbeatHandler;

    @Resource
    private LoginHandler loginHandler;


    @Override
    protected void initChannel(SocketChannel channel) {

        channel.pipeline()
                // MDC日志上下文
                .addLast(MdcLoggingHandler.NAME, mdcLoggingHandler)
                //自定义长度解码器
                .addLast(LengthFieldDecoderHandler.NAME, new LengthFieldDecoderHandler())
                // 编解码
                .addLast(PacketCodecHandler.NAME, packetCodecHandler)
                //                设定IdleStateHandler心跳检测每15秒进行一次读检测，如果15秒内ChannelRead()方法未被调用则触发一次userEventTrigger()方法
                .addLast(new IdleStateHandler(15, 0, 0))
                // 心跳处理器
                .addLast(heartbeatHandler)
//                登录处理器
                .addLast(loginHandler)
                // 业务处理器
                .addLast(BizServerHandler.NAME, bizServerHandler);
        // 连接异常处理

    }

}
