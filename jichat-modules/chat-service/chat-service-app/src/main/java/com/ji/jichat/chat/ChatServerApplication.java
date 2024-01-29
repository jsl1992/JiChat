package com.ji.jichat.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ChatServerApplication
 *
 * @author jisl on 2024/1/29 12:25
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ji.mall.product.mapper")
@EnableFeignClients(basePackages = "com.ji.jichat")
public class ChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }

}

