package com.example.config;

import com.example.i18n.AppLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author 李磊
 * @datetime 2020/2/28 17:24
 * @description
 */
@Configuration
public class I18nConfig {
    @Bean
    public LocaleResolver localeResolver() {
        // SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        // 设置默认区域
        // sessionLocaleResolver.setDefaultLocale(Locale.CHINA);
        // return sessionLocaleResolver;
        return new AppLocaleResolver();
    }
}