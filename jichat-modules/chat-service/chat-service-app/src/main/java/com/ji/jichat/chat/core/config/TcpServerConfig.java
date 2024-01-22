package com.ji.jichat.chat.core.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 服务器配置信息
 *
 * @author lintengyue
 */
@Component
@Data
@NoArgsConstructor
public class TcpServerConfig {

    @Value("${outside-ip}")
    private String outsideIp;

    @Value("${inner-ip}")
    private String innerIp;

    @Value("${tcpServer.tcpPort}")
    private int tcpPort;

    @Value("${tcpServer.httpPort}")
    private int httpPort;

    @Value("${tcpServer.bossThreadCount:1}")
    private int bossCount;

    @Value("${tcpServer.workerThreadCount:0}")
    private int workerCount;

    @Value("${tcpServer.keepalive}")
    private boolean keepAlive;

    @Value("${tcpServer.backlog}")
    private int backlog;

    @Value("${spring.profiles.active}")
    private String env;

    public String getTcpAddress() {
        return this.outsideIp + ":" + this.tcpPort;
    }

    public String getHttpAddress() {
        return this.innerIp + ":" + this.httpPort;
    }


}
