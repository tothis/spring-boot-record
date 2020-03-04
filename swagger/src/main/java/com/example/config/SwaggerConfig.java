package com.example.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
                .license("Apache License 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
        // Predicate<RequestHandler> selector1 = RequestHandlerSelectors.basePackage("com.example.mobile.controller");
        // Predicate<RequestHandler> selector2 = RequestHandlerSelectors.basePackage("com.example.admin.controller");
        return new Docket(DocumentationType.SWAGGER_2)
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
                .globalOperationParameters(parameters);
    }

    @Bean
    public Docket createAdminApi() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("后台管理").build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.ant("/admin/**"))
                .build();
    }

    /* 更改swagger访问地址 本质为使用自定义接口转发 */

    private final String DEFAULT_PATH = "test";

    @Value("${swagger.mapping.order:10}")
    private int order;

    /**
     * SwaggerUI资源访问
     *
     * @param servletContext
     */
    @Bean
    public SimpleUrlHandlerMapping swaggerUrlHandlerMapping(ServletContext servletContext) throws Exception {
        SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
        Map<String, ResourceHttpRequestHandler> urlMap = new HashMap<>();
        {
            PathResourceResolver pathResourceResolver = new PathResourceResolver();
            pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/webjars/"));
            pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

            ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
            resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/webjars/")));
            resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
            resourceHttpRequestHandler.setServletContext(servletContext);
            resourceHttpRequestHandler.afterPropertiesSet();
            // 设置新的路径
            urlMap.put(DEFAULT_PATH + "/webjars/**", resourceHttpRequestHandler);
        }
        {
            PathResourceResolver pathResourceResolver = new PathResourceResolver();
            pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/"));
            pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

            ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
            resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/")));
            resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
            resourceHttpRequestHandler.setServletContext(servletContext);
            resourceHttpRequestHandler.afterPropertiesSet();
            // 设置新的路径
            urlMap.put(DEFAULT_PATH + "/**", resourceHttpRequestHandler);
        }
        urlHandlerMapping.setUrlMap(urlMap);
        // 调整DispatcherServlet关于SimpleUrlHandlerMapping的排序
        urlHandlerMapping.setOrder(order);
        return urlHandlerMapping;
    }

    @ApiIgnore
    @RequestMapping(DEFAULT_PATH)
    @RestController
    private class SwaggerResourceController implements InitializingBean {

        @Autowired
        private ApiResourceController apiResourceController;

        @Autowired
        private Environment environment;

        @Autowired
        private DocumentationCache documentationCache;

        @Autowired
        private ServiceModelToSwagger2Mapper mapper;

        @Autowired
        private JsonSerializer jsonSerializer;

        private Swagger2Controller swagger2Controller;

        @Override
        public void afterPropertiesSet() {
            swagger2Controller = new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer);
        }

        @GetMapping("swagger-resources/configuration/security")
        public ResponseEntity<SecurityConfiguration> securityConfiguration() {
            return apiResourceController.securityConfiguration();
        }

        @GetMapping("swagger-resources/configuration/ui")
        public ResponseEntity<UiConfiguration> uiConfiguration() {
            return apiResourceController.uiConfiguration();
        }

        @GetMapping("swagger-resources")
        public ResponseEntity<List<SwaggerResource>> swaggerResources() {
            return apiResourceController.swaggerResources();
        }

        @GetMapping("v2/api-docs")
        public ResponseEntity<Json> getDocumentation(HttpServletRequest request, String group) {
            return swagger2Controller.getDocumentation(group, request);
        }
    }
}