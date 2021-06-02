package com.example.config;

import com.example.converter.StringConverter;
import com.example.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李磊
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${lilei.trim.enabled}")
    private boolean trimEnabled;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Controller方法不接受JSON参数时会使用converter转化
        if (trimEnabled) {
            registry.addConverter(new StringConverter());
        }
    }

    @Bean
    public FilterRegistrationBean<TokenFilter> testFilterRegistrationBean(TokenFilter tokenFilter) {
        FilterRegistrationBean<TokenFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(tokenFilter);
        // 可访问前缀为 /api 的路径
        registration.addUrlPatterns("/api/*");
        return registration;
    }
}