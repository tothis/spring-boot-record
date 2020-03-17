package com.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /**
     * 创建user数据源配置对象
     */
    @Primary
    @Bean(name = "userDataSourceProperties")
    // 读取spring.datasource.user配置到DataSourceProperties对象
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 创建user数据源
     */
    @Primary
    @Bean(name = "userDataSource")
    // 读取spring.datasource.user配置到HikariDataSource对象
    @ConfigurationProperties(prefix = "spring.datasource.user.hikari")
    public DataSource userDataSource() {
        // 获得DataSourceProperties对象
        DataSourceProperties properties = this.userDataSourceProperties();
        // 创建HikariDataSource对象
        return createHikariDataSource(properties);
    }

    /**
     * 创建quartz数据源配置对象
     */
    @Bean(name = "quartzDataSourceProperties")
    // 读取spring.datasource.quartz配置到DataSourceProperties对象
    @ConfigurationProperties(prefix = "spring.datasource.quartz")
    public DataSourceProperties quartzDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 创建quartz数据源
     */
    @Bean(name = "quartzDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.quartz.hikari")
    @QuartzDataSource
    public DataSource quartzDataSource() {
        // 获得DataSourceProperties对象
        DataSourceProperties properties = this.quartzDataSourceProperties();
        // 创建HikariDataSource对象
        return createHikariDataSource(properties);
    }

    private HikariDataSource createHikariDataSource(DataSourceProperties properties) {
        // 创建HikariDataSource对象
        HikariDataSource dataSource = properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
        // 设置线程池名
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }
}