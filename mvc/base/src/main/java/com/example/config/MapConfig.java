package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author 李磊
 */
@Data // 必须有set方法
@Configuration
@ConfigurationProperties("spring")
public class MapConfig {
    private Map<String, String> mvc;
    private Map<String, String> servlet;
}
