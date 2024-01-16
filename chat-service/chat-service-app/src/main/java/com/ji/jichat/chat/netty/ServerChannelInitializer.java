package com.ji.jichat.chat.netty;



import com.ji.jichat.chat.core.config.TcpServerConfig;
import com.ji.jichat.chat.netty.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("serverChannelInitializer")
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private TcpServerConfig tcpServerConfig;

    @Autowired
    @Qualifier("bizServerHandler")
    private BizServerHandler bizServerHandler;


    @Autowired
    private MdcLoggingHandler mdcLoggingHandler;

    @Autowired
    private PacketCodecHandler packetCodecHandler;


    @Override
    protected void initChannel(SocketChannel channel) {

        channel.pipeline()
//            统计连接时间
            .addLast(new ConnectionTimeHandler())
            // MDC日志上下文
//            .addLast(MdcLoggingHandler.NAME, mdcLoggingHandler)
            //自定义长度解码器
            .addLast(LengthFieldDecoderHandler.NAME, new LengthFieldDecoderHandler())
            // 编解码
            .addLast(PacketCodecHandler.NAME, packetCodecHandler)
            // 心跳处理器
//            .addLast(HeartBeatServerHandler.NAME, new HeartBeatServerHandler())
            // 业务处理器
            .addLast(BizServerHandler.NAME, bizServerHandler);
        // 连接异常处理

    }

}
