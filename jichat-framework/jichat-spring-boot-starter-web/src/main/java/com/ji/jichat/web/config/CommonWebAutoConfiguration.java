package com.ji.jichat.web.config;


import com.ji.jichat.web.core.aspect.AccessLogAspect;
import com.ji.jichat.web.core.handler.GlobalExceptionHandler;
import com.ji.jichat.web.core.handler.GlobalResponseBodyHandler;
import com.ji.jichat.web.core.interceptor.FeignRequestInterceptor;
import com.ji.jichat.web.core.interceptor.TraceSpanInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    // ========== MessageConverter 相关 ==========

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // 创建 FastJsonHttpMessageConverter 对象
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        // 自定义 FastJson 配置
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setCharset(Charset.defaultCharset()); // 设置字符集
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect, // 剔除循环引用
//                SerializerFeature.WriteNonStringKeyAsString); // 解决 Integer 作为 Key 时，转换为 String 类型，避免浏览器报错
//        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        // 设置支持的 MediaType
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        // 添加到 converters 中
//        converters.add(0, fastJsonHttpMessageConverter); // 注意，添加到最开头，放在 MappingJackson2XmlHttpMessageConverter 前面
//    }

}
