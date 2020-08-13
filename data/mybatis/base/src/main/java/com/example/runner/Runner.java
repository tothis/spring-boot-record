package com.example.runner;

import com.example.util.MyBatisUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 李磊
 * @datetime 2020/03/18 22:20
 * @description
 */
@Component
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        MyBatisUtil.exec("data.sql", MyBatisUtil.ExecType.file);
    }
}