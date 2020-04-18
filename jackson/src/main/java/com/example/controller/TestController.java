package com.example.controller;

import com.example.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 李磊
 * @datetime 2020/3/16 22:26
 * @description
 */
@RestController
public class TestController {

    @GetMapping("date")
    public Date date(Date date) {
        return date;
    }

    @GetMapping("localDateTime")
    public LocalDateTime localDateTime(LocalDateTime localDateTime) {
        return localDateTime;
    }

    /**
     * @param user
     * @return
     */
    @PostMapping("user")
    public User user(@RequestBody User user) {
        System.out.println(user);
        user.setRemark("remark");
        return user;
    }
}