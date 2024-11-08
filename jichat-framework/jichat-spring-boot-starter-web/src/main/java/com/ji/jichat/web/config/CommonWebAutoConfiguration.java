package com.ji.jichat.web.config;


import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ji.jichat.web.core.aspect.AccessLogAspect;
import com.ji.jichat.web.core.handler.GlobalExceptionHandler;
import com.ji.jichat.web.core.handler.GlobalResponseBodyHandler;
import com.ji.jichat.web.core.interceptor.FeignRequestInterceptor;
import com.ji.jichat.web.core.interceptor.TraceSpanInterceptor;
import com.ji.jichat.web.jackson.CustomDateDeserializer;
import com.ji.jichat.web.jackson.CustomDateSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

/**
 * web全局配置
 *
 * @author jisl on 2021/1/23 15:35
 **/
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Slf4j
public class CommonWebAutoConfiguration implements WebMvcConfigurer {


    // ========== 全局处理器 ==========

    @Bean
    @ConditionalOnMissingBean(GlobalResponseBodyHandler.class)
    public GlobalResponseBodyHandler globalResponseBodyHandler() {
        return new GlobalResponseBodyHandler();
    }

    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    // ========== 拦截器相关 ==========


    @Bean
    @ConditionalOnMissingBean(AccessLogAspect.class)
    public AccessLogAspect accessLogAspect() {
        return new AccessLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean(FeignRequestInterceptor.class)
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(TraceSpanInterceptor.class)
    public TraceSpanInterceptor traceSpanInterceptor() {
        return new TraceSpanInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        try {
            registry.addInterceptor(this.traceSpanInterceptor()).order(Ordered.HIGHEST_PRECEDENCE);
            log.info("[addInterceptors][加载 TraceSpanInterceptor 拦截器完成]");
        } catch (NoSuchBeanDefinitionException e) {
            log.warn("[addInterceptors][无法获取 TraceSpanInterceptor 拦截器，因此不启动 TraceSpan 的记录]");
        }
    }


    // ========== 过滤器相关 ==========

    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许跨域请求的来源，"*" 表示允许任何来源
        config.addAllowedOrigin("*");
        // 允许的HTTP方法，如 GET、POST 等
        config.addAllowedMethod("*");
        // 允许的头部信息，如 "Content-Type"、"Authorization" 等
        config.addAllowedHeader("*");
        // 是否支持携带凭证（如 cookies）
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    /**
     * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题 * @return Jackson2ObjectMapperBuilderCustomizer 注入的对象
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return builder -> {
            // 配置Long类型转换为字符串
            builder.serializerByType(Long.class, ToStringSerializer.instance);
//            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);

            // 创建SimpleModule并注册自定义序列化器和反序列化器
            SimpleModule module = new SimpleModule();
            module.addSerializer(Date.class, new CustomDateSerializer());
            module.addDeserializer(Date.class, new CustomDateDeserializer());

            // 注册模块
            builder.modules(module);
        };
    }

}
