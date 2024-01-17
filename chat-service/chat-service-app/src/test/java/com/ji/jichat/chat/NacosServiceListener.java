package com.ji.jichat.chat;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
/**
  * 监听nacos服务变化
 * 注册服务方法 post:http://192.168.137.130:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080
  * @author jisl on 2024/1/17 13:58
  **/
public class NacosServiceListener {

    public static void main(String[] args) {
        try {
            String serverAddr = "192.168.137.130:8848"; // Nacos服务器地址
            String serviceName = "nacos.naming.serviceName"; // 服务名

            // 创建Nacos NamingService
            NamingService namingService = NamingFactory.createNamingService(serverAddr);

            // 订阅服务变化
            namingService.subscribe(serviceName, event -> {
                if (event instanceof NamingEvent) {
//                System.out.println(((NamingEvent) event).getServceName());
                    System.out.println("服务列表:" + ((NamingEvent) event).getInstances());
                }
            });

            // 保持程序运行，监听服务变化
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
