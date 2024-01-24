package com.ji.jichat.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ji.jichat.mybatis.core.handler.DefaultDBFieldHandler;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBaits 配置类
 *
 * @author 芋道源码
 */
@Configuration
@MapperScan(value = "com.ji.jichat.*.mapper", annotationClass = Mapper.class) // Mapper 懒加载，目前仅用于单元测试
public class MybatisAutoConfiguration {

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
//        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor()); // 分页插件
//        return mybatisPlusInterceptor;
//    }

    @Bean
    public MetaObjectHandler defaultMetaObjectHandler() {
        return new DefaultDBFieldHandler(); // 自动填充参数类
    }


}
