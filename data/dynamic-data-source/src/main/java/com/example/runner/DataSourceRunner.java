package com.example.runner;

import com.example.config.DynamicDataSource;
import com.example.mapper.TableMapper;
import com.example.model.Table;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Component
// springboot启动后会执行CommandLineRunner实现类的run方法
public class DataSourceRunner implements CommandLineRunner {

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Autowired
    private TableMapper tableMapper;

    private static ExecutorService executor = Executors.newFixedThreadPool(4);

    public boolean dataSourceTask() {
        List<Table> tables = tableMapper.findAll();
        Map<Object, Object> dataSourceMap = tables.parallelStream().collect(Collectors.toMap(
                Table::getId, dbManager -> {
                    HikariDataSource hikariDataSource = new HikariDataSource();
                    hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                    hikariDataSource.setJdbcUrl(dbManager.getUrl());
                    hikariDataSource.setUsername(dbManager.getUserName());
                    hikariDataSource.setPassword(dbManager.getPassword());
                    hikariDataSource.setMaximumPoolSize(12);
                    hikariDataSource.setConnectionTimeout(60000);
                    hikariDataSource.setMinimumIdle(10);
                    hikariDataSource.setIdleTimeout(500000);
                    hikariDataSource.setMaxLifetime(540000);
                    hikariDataSource.setConnectionTestQuery("SELECT 1");
                    return hikariDataSource;
                }
        ));

        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.afterPropertiesSet();
        return true;
    }

    @Override
    public void run(String... args) throws Exception {
        executor.submit(this::dataSourceTask).get();
    }
}