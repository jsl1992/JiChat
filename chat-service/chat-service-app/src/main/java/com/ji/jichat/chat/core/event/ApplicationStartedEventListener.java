package com.ji.jichat.chat.core.event;//package com.xiangan.traffic.core.config.event;
//
//
//import com.xiangan.common.utils.RedisUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
///**
// *
// */
//@Slf4j
//@Component
//public class ApplicationStartedEventListener implements ApplicationListener<ContextRefreshedEvent> {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    private TcpServerConfig tcpServerConfig;
//
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        log.info("ApplicationStartedEvent");
//        RedisUtils.setRedisTemplate(stringRedisTemplate);
//        ChannelRepository.setTcpAddress(tcpServerConfig);
//    }
//
//}
