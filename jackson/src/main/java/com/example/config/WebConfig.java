package com.example.config;

import com.example.converter.DateConverter;
import com.example.converter.LocalDateTimeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李磊
 * @datetime 2020/3/16 22:07
 * @description
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // controller方法get请求时会使用converter转化
        registry.addConverter(new DateConverter());
        registry.addConverter(new LocalDateTimeConverter());
    }
}