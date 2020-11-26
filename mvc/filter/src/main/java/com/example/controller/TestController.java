package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 李磊
 * @since 1.0
 */
@RestController
public class TestController {
    @PostMapping
    Map test(@RequestBody Map params) {
        return params;
    }

    @GetMapping
    void get() {
    }
}