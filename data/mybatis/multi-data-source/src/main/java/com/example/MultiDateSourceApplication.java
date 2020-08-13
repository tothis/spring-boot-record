package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan(basePackages = "com.example.mapper")
/**
 * DataSourceAutoConfiguration会自动配置 实现多数据源需排除它
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MultiDateSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDateSourceApplication.class, args);
    }
}