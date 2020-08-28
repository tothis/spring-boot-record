package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

/**
 * @author 李磊
 * @datetime 2020/1/16 22:03
 * @description
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createApi() {
        Predicate<RequestHandler> predicate = RequestHandlerSelectors.basePackage("com.example.controller");
        return new Docket(DocumentationType.SWAGGER_2).select().apis(predicate).build();
    }
}