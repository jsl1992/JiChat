package com.ji.jichat.security.admin.config;


import com.ji.jichat.security.admin.core.interceptor.SecurityInterceptor;
import com.ji.jichat.web.config.CommonWebAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AutoConfigureAfter(CommonWebAutoConfiguration.class) // 在 CommonWebAutoConfiguration 之后自动配置，保证过滤器的顺序
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Bean
    @ConditionalOnMissingBean
    public SecurityProperties adminSecurityProperties() {
        return new SecurityProperties();
    }

    // ========== 拦截器相关 ==========

    @Bean
    public SecurityInterceptor adminSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SecurityProperties properties = this.adminSecurityProperties();
        // AdminSecurityInterceptor 拦截器
        registry.addInterceptor(this.adminSecurityInterceptor())
                .excludePathPatterns(properties.getIgnorePaths())
                .excludePathPatterns(properties.getDefaultIgnorePaths());
        logger.info("[addInterceptors][加载 AdminSecurityInterceptor 拦截器完成]");

    }

}
