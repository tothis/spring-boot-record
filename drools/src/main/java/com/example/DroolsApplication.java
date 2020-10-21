package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 李磊
 * @since 1.0
 */
@SpringBootApplication
public class DroolsApplication {
    public static void main(String[] args) {
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
        SpringApplication.run(DroolsApplication.class, args);
    }
}