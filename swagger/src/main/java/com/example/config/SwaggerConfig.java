package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置 https://github.com/springfox/springfox
 *
 * @author 李磊
 * @since 1.0
 */
@Configuration
public class SwaggerConfig {

    /**
     * 访问路径
     * http://localhost:8080/swagger-ui/
     * http://localhost:8080/swagger-ui/index.html
     */
    @Bean
    public Docket test() {
        // 当每个接口都需token参数时 可通过如下配置
        // swagger添加head参数 保留用户token
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
        RequestParameter build = parameterBuilder
                // 参数类型支持header cookie body query
                .in(ParameterType.HEADER)
                .name("token") // 参数名
                .description("令牌") // 描述
                .required(false) // 非必需 login不需要验证
                .build();
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(build);

        // 创建该api的基本信息
        ApiInfo apiInfo = new ApiInfoBuilder()
                // 页面标题
                .title("页面标题")
                .termsOfServiceUrl("网址")
                .description("描述")
                .contact(new Contact("李磊", "url", "mail"))
                .version("1.0")
                .license("Apache License 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
        /*Predicate<RequestHandler> selector1 = RequestHandlerSelectors
                .basePackage("com.example.mobile.controller");
        Predicate<RequestHandler> selector2 = RequestHandlerSelectors
                .basePackage("com.example.admin.controller");*/
        return new Docket(DocumentationType.OAS_30)
                // 存在多个Docket实例时 每个实例需具有唯一groupName 默认为'default'
                .groupName("mobile")
                // apiInfo() 增加API相关信息
                .apiInfo(apiInfo)
                // .pathMapping("doc") // 前端接口访问前缀
                // select()获取ApiSelectorBuilder实例 配置接口
                .select()
                // 配置扫描controller包
                // .apis(RequestHandlerSelectors.any()) // 扫描所有包
                // .apis(Predicates.or(selector1, selector2)) // 匹配多个包
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                // 匹配所有接口
                // .paths(PathSelectors.any())
                // 只匹配前缀为test的接口
                .paths(PathSelectors.ant("/test/**"))
                .build()
                .globalRequestParameters(parameters);
    }

    @Bean
    public Docket admin() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("后台管理").build();
        return new Docket(DocumentationType.OAS_30)
                .groupName("admin")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.ant("/admin/**"))
                .build();
    }
}