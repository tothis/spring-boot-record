package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class HibernateJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateJpaApplication.class, args);
    }
}