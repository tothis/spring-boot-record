package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.example.repository") // 扫描jpa接口
@SpringBootApplication
public class MongoDBLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDBLearnApplication.class, args);
    }

}