package com.ji.jichat.chat;


import com.ji.jichat.chat.core.config.TcpServerConfig;
import com.ji.jichat.chat.netty.ServerChannelInitializer;
import com.ji.jichat.chat.netty.listener.NettyCloseFutureListener;
import com.ji.jichat.chat.netty.listener.NettyStartFutureListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Socket服务器
 *
 * @author lintengyue
 */
@Slf4j
@Component
public class TCPServer implements CommandLineRunner {

    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    @Autowired
    private TcpServerConfig tcpServerConfig;

    @Autowired
    @Qualifier("serverChannelInitializer")
    private ServerChannelInitializer serverChannelInitializer;

    @Autowired
    private NettyStartFutureListener nettyStartFutureListener;

    @Autowired
    private NettyCloseFutureListener nettyCloseFutureListener;

    @Override
    public void run(String... args) {
        start();
    }

    public void start() {

        // 默认以cpu核心数*2为工作线程池大小
        final int core = Runtime.getRuntime().availableProcessors();
        tcpServerConfig.setWorkerCount(core * 2);

        log.info("Netty 起飞 [{}]", tcpServerConfig);
        // 创建两个线程组，用于接收客户端的连接和处理 I/O 操作
        // 用于接收连接
        bossGroup = new NioEventLoopGroup(tcpServerConfig.getBossCount());
        // 用于处理连接后的 I/O 操作
        workerGroup = new NioEventLoopGroup(tcpServerConfig.getWorkerCount());
        // 创建 ServerBootstrap 实例
        serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, tcpServerConfig.isKeepAlive())
                .option(ChannelOption.SO_BACKLOG, tcpServerConfig.getBacklog())
//                    .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(serverChannelInitializer);

        // 绑定端口并启动服务
        ChannelFuture channelFuture = serverBootstrap.bind(tcpServerConfig.getTcpPort()).addListener(nettyStartFutureListener);

        // 等待服务端关闭
        channelFuture.channel().closeFuture().addListener(nettyCloseFutureListener);

    }

    public void shutdownGracefully() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }


}
