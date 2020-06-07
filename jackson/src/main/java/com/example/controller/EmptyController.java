package com.example.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/5/19 22:27
 * @description
 */
@RequestMapping("empty")
@RestController
public class EmptyController {

    @GetMapping("user")
    public User user() {
        User result = new User();
        result.setUserName("lilei");
        return result;
    }

    @Data
    class User {
        private String userName;
        private List<String> role;
    }
}