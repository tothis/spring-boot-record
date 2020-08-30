package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

/**
 * swagger配置
 *
 * @author 李磊
 * @since 1.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        Predicate<RequestHandler> predicate = RequestHandlerSelectors
                .basePackage("com.example.controller");
        return new Docket(DocumentationType.OAS_30).select()
                .apis(predicate).build();
    }
}