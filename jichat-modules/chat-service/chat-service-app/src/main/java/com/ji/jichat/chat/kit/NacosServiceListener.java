package com.ji.jichat.chat.kit;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NacosServiceListener {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;


    @Value("${spring.cloud.nacos.discovery.namespace}")
    private String namespace;

    private static final String SERVICE_NAME = "chat-service";

    @Resource
    private ServerLoadBalancer serverLoadBalancer;


    @PostConstruct
    public void init() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        properties.put("namespace", namespace);
        // 创建Nacos NamingService
        NamingService namingService = NamingFactory.createNamingService(properties);
        // 订阅服务变化
        namingService.subscribe(SERVICE_NAME, event -> {
            if (event instanceof NamingEvent) {
                final NamingEvent namingEvent = (NamingEvent) event;
                List<Instance> instances = namingEvent.getInstances();
                log.info("服务个数{},服务列表:{}", instances.size(), instances);
                final List<String> curInstances = instances.stream().map(t -> t.getIp() + ":" + t.getPort()).collect(Collectors.toList());
                serverLoadBalancer.syncServer(curInstances);
            }
        });
    }
}
