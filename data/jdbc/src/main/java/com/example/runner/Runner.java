package com.example.runner;

import com.example.util.FileUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * @author 李磊
 * @datetime 2020/03/18 22:20
 * @description
 */
@Component
public class Runner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public Runner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws FileNotFoundException {
        jdbcTemplate.execute(FileUtil.readString(ResourceUtils
                .getFile("classpath:data.sql")));
    }
}