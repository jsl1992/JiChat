package com.ji.jichat.security.admin.config;

import com.ji.jichat.security.admin.rpc.LoginUserRequestInterceptor;
import com.ji.jichat.user.api.UserRpc;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Security 使用到 Feign 的配置项
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {UserRpc.class})
public class SecurityRpcAutoConfiguration {

    @Bean
    public LoginUserRequestInterceptor loginUserRequestInterceptor() {
        return new LoginUserRequestInterceptor();
    }

}
