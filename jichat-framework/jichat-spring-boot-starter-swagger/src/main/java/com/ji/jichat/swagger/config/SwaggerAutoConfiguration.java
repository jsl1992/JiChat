package com.ji.jichat.swagger.config;

import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * 简单的 Swagger2 自动配置类
 * <p>
 * 较为完善的，可以了解 https://mvnrepository.com/artifact/com.spring4all/spring-boot-starter-swagger
 *
 * @author jisl
 */
@Configuration
@ConditionalOnClass({OpenAPI.class})
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger", value = "enable", matchIfMissing = true)
public class SwaggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }


    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
//    @Bean
//    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
//        return openApi -> {
//            if (openApi.getTags() != null) {
//                openApi.getTags().forEach(tag -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("x-order", RandomUtil.randomInt(0, 100));
//                    tag.setExtensions(map);
//                });
//            }
//            if (openApi.getPaths() != null) {
//                openApi.addExtension("x-test123", "333");
//                openApi.getPaths().addExtension("x-abb", RandomUtil.randomInt(1, 100));
//            }
//
//        };
//    }


    @Bean
    public OpenAPI createApi() {
        SwaggerProperties properties = swaggerProperties();
        // 创建 Docket 对象
        return new OpenAPI()
                .info(buildInfo(properties));
    }

    private Info buildInfo(SwaggerProperties properties) {
        return new Info()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .contact(new Contact().name(properties.getAuthor()).url(properties.getUrl()).email(properties.getEmail()))
                .license(new License().name(properties.getLicense()));
    }

}
