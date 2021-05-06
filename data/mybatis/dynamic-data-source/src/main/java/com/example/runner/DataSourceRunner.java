package com.example.runner;

import com.example.config.DataSourceSwitch;
import com.example.config.DynamicDataSource;
import com.example.mapper.TableMapper;
import com.example.model.Table;
import com.example.util.MyBatisUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Spring Boot 启动后会执行CommandLineRunner实现类的run方法
 *
 * @author 李磊
 */
@Slf4j
@Component
public class DataSourceRunner implements CommandLineRunner {

    private static ExecutorService executor = Executors.newFixedThreadPool(4);
    private final DynamicDataSource dynamicDataSource;
    private final TableMapper tableMapper;

    public DataSourceRunner(DynamicDataSource dynamicDataSource, TableMapper tableMapper) {
        this.dynamicDataSource = dynamicDataSource;
        this.tableMapper = tableMapper;
    }

    public boolean dataSourceTask() {
        List<Table> tables = tableMapper.findAll();
        DataSourceSwitch.setDataSource(tables.stream().map(Table::getId).collect(Collectors.toList()));
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
        MyBatisUtil.exec("data.sql", MyBatisUtil.ExecType.FILE);
        executor.submit(this::dataSourceTask).get();
    }
}