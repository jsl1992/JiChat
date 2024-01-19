package com.ji.jichat.user;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jisl on 2024/1/17 19:16
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ji.jichat.user.mapper")
//@EnableFeignClients(basePackages = "com.ji.jichat")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
