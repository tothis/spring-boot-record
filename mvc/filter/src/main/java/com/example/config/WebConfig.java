package com.example.config;

import com.example.constant.FilterConstant;
import com.example.filter.TokenFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李磊
 */
@Configuration
public class WebConfig {

    @Bean
    @ConditionalOnProperty(value = "lilei.token.enabled", havingValue = "true")
    public FilterRegistrationBean<TokenFilter> testFilterRegistrationBean(TokenFilter tokenFilter) {
        FilterRegistrationBean<TokenFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(tokenFilter);
        /*
         * /api 拦截 /api
         * /api/ 拦截 /api/
         * /api/* 拦截 前缀为 /api 的路径
         */
        registration.addUrlPatterns("/api/*");
        registration.setOrder(FilterConstant.TOKEN_SORT);
        return registration;
    }
}
