package com.ji.jichat.chat.core.event;

import com.ji.jichat.chat.TCPServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@Component
public class ApplicationCloseEventListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private TCPServer tcpServer;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("ApplicationCloseEvent");
        tcpServer.shutdownGracefully();
    }

}
