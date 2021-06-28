package com.example.configuration;

import com.example.component.ThisTemplate;
import com.example.properties.ThisProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配类
 *
 * @author 李磊
 **/
@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(ThisProperties.class)
public class ThisConfiguration {

    private final ThisProperties properties;

    @Bean
    // spring 容器中没有 ThisTemplate 类的对象时才调用该方法
    @ConditionalOnMissingBean(ThisTemplate.class)
    public ThisTemplate template() {
        log.info("用户信息 : {}", properties);
        return new ThisTemplate();
    }
}
