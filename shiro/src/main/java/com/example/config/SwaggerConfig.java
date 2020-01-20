package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
        return new Docket(DocumentationType.SWAGGER_2)
                // apiInfo() 增加API相关信息
                .apiInfo(apiInfo())
                // 通过select()函数返回一个ApiSelectorBuilder实例 用来控制哪些接口暴露给Swagger来展现
                .select()
                // 配置controller包 指定扫描的包路径来定义指定要建立API的目录
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息 http://localhost:8080/swagger-ui.html
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("mybatis-plus使用")
                // 创建
                .termsOfServiceUrl("网址")
                // 描述
                .description("mybatis-plus使用记录")
                .contact(new Contact("李磊", "url", "mail"))
                .version("1.0")
                .build();
    }
}