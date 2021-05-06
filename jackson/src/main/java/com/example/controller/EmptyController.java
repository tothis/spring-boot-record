package com.example.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 */
@RequestMapping("empty")
@RestController
public class EmptyController {

    @GetMapping("user")
    public List<User> user() {
        List<User> result = new ArrayList<>();

        User u1 = new User();
        u1.setUserName("lilei");
        result.add(u1);

        User u2 = new User();
        u2.setAddresses(new String[]{"北京"});
        result.add(u2);

        result.add(new User());

        return result;
    }

    @Data
    class User {
        private String userName;
        private List<String> role;
        private String[] addresses;
    }
}