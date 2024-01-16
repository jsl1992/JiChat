package com.ji.jichat.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序
 * 交调设备项目
 *
 * @author xiangan
 */
@SpringBootApplication
@MapperScan("com.ji.mall.product.mapper")
public class ChatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceApplication.class, args);
    }

}

