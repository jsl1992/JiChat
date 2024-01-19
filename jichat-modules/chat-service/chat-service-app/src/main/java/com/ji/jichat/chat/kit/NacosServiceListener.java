package com.ji.jichat.chat.kit;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
public class NacosServiceListener {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;


    @Value("${spring.cloud.nacos.discovery.namespace}")
    private String namespace;

    @Value("${spring.application.name}")
    private String serviceName;


    @PostConstruct
    public void init() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        properties.put("namespace", namespace);
        // 创建Nacos NamingService
        NamingService namingService = NamingFactory.createNamingService(properties);
        // 订阅服务变化
        namingService.subscribe(serviceName, event -> {
            if (event instanceof NamingEvent) {
                final NamingEvent namingEvent = (NamingEvent) event;
                System.out.println("服务个数:" + namingEvent.getInstances().size());
                System.out.println("服务列表:" + namingEvent.getInstances());
                for (Instance instance : namingEvent.getInstances()) {
                    ConsistentHashing.addNode(instance.getIp() + ":" + instance.getPort());
                }
            }
        });
    }
}
