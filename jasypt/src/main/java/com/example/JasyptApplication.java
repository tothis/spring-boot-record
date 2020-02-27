package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class JasyptApplication {

    public static void main(String[] args) {
        SpringApplication.run(JasyptApplication.class, args);
    }

    @Value("${test}")
    private String test;

    @GetMapping
    public String test() {
        return test;
    }
}