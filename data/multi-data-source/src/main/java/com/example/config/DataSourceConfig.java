package com.example.config;

import com.example.type.DataSourceEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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
     * @Qualifier 根据名称注入 具有相同类型的多个实例时 使用名称注入
     */
    @Bean
    public MultiDataSource multiDataSource(@Qualifier("db1") DataSource db1, @Qualifier("db2") DataSource db2) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.db1, db1);
        targetDataSources.put(DataSourceEnum.db2, db2);
        MultiDataSource dataSource = new MultiDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 设置默认的datasource
        dataSource.setDefaultTargetDataSource(db1);
        return dataSource;
    }

    /**
     * 配置事务管理器 不需要事务可省略此配置
     */
    @Bean
    public DataSourceTransactionManager transactionManager(MultiDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}