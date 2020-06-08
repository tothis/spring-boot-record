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
 * @author lilei
 * @time 2019/11/10 17:52
 **/
@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(ThisProperties.class)
public class ThisConfiguration {

    private final ThisProperties properties;

    @Bean
    // spring容器中没有ThisTemplate类的对象时 才调用该方法
    @ConditionalOnMissingBean(ThisTemplate.class)
    public ThisTemplate template() {
        log.info("用户信息 : {}", properties);
        return new ThisTemplate();
    }
}