package com.example.config;

import com.example.convert.EnumConverterFactory;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/10 9:52
 * @description
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload-file-path}")
    private String filePath;

    /**
     * 静态资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置server虚拟路径 handler为浏览器访问路径 locations为对应的本地目录
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + filePath);
    }

    /**
     * addCorsMappings配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowCredentials(true);
    }

    /**
     * 使用cors filter跨域
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    // springboot默认不会实例化RestTemplate
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    // 注册自定义类型转化器
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加device和site拦截器
        registry.addInterceptor(new DeviceResolverHandlerInterceptor());
        registry.addInterceptor(new SitePreferenceHandlerInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // controller入参自动注入device和site
        argumentResolvers.add(new DeviceHandlerMethodArgumentResolver());
        argumentResolvers.add(new SitePreferenceHandlerMethodArgumentResolver());
    }

    @Bean
    public ContentNegotiatingViewResolver viewResolver(ThymeleafViewResolver thymeleafViewResolver) {
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        List<ViewResolver> result = new ArrayList<>();
        result.add(new LiteDeviceDelegatingViewResolver(thymeleafViewResolver) {{
            setMobilePrefix("mobile/");
            setTabletPrefix("tablet/");
        }});
        viewResolver.setViewResolvers(result);
        return viewResolver;
    }
}