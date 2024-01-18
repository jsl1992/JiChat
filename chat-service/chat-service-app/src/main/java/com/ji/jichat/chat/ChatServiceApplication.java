package com.ji.jichat.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动程序
 * 交调设备项目
 *
 * @author xiangan
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ji.mall.product.mapper")
@EnableFeignClients(basePackages = "com.ji.jichat")
public class ChatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceApplication.class, args);
    }

}

