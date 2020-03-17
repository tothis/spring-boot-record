package com.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    /**
     * @return
     * @throws Exception
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候 默认选择哪一个 而不是让@autowire注解报错
     */
    @Primary
    @Bean("db1")
    @ConfigurationProperties("spring.datasource.db1")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("db2")
    @ConfigurationProperties("spring.datasource.db2")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置事务管理器 不需要事务可省略此配置
     */
    @Bean
    public DataSourceTransactionManager db1TransactionManager(@Qualifier("db1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DataSourceTransactionManager db2TransactionManager(@Qualifier("db2") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}