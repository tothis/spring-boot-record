package com.example.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus配置
 *
 * @author 李磊
 */
@Slf4j
@Configuration
@MapperScan("tech.yunx.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Value("${app.snowflake.work-id}")
    private long workerId;
    @Value("${app.snowflake.data-center-id}")
    private long dataCenterId;

    @Bean
    public IdentifierGenerator idGenerator() {
        log.info("初始化雪花ID配置 workerId->[{}] dataCenterId->[{}]", workerId, dataCenterId);
        // 初始化雪花ID生成工具配置
        IdWorker.initSequence(workerId, dataCenterId);
        return new DefaultIdentifierGenerator(workerId, dataCenterId);
    }
}