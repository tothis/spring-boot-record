package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2019/12/24 23:43
 * @description
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createApi() {
        // 当每个接口都需token参数时 可通过如下配置
        // swagger添加head参数 保留用户token
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        Parameter build = parameterBuilder
                // 参数类型支持header cookie body query
                .parameterType("header")
                .name("token") // 参数名
                .defaultValue("token") // 默认值
                .description("令牌") // 描述
                .modelRef(new ModelRef("string")) // 指定参数值的类型
                .required(false) // 非必需 login不需要验证
                .build();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(build);

        // 创建该api的基本信息 http://localhost:8080/swagger-ui.html
        ApiInfo apiInfo = new ApiInfoBuilder()
                // 页面标题
                .title("页面标题")
                .termsOfServiceUrl("网址")
                .description("描述")
                .contact(new Contact("李磊", "url", "mail"))
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                // 存在多个Docket实例时 每个实例需具有唯一groupName 默认为'default'
                .groupName("doc-dev")
                // apiInfo() 增加API相关信息
                .apiInfo(apiInfo)
                // .pathMapping("doc") // 前端接口访问前缀
                // select()获取ApiSelectorBuilder实例 配置接口
                .select()
                // 配置扫描controller包
                // .apis(RequestHandlerSelectors.any()) // 扫描所有包
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                // 匹配所有接口
                // .paths(PathSelectors.any())
                // 只匹配前缀为test的接口
                .paths(PathSelectors.ant("/test/**"))
                .build()
                .globalOperationParameters(parameters);
    }
}