package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:52
 * @description
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("insert")
    public int insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @GetMapping("list")
    public List<User> list() {
        return userService.list();
    }
}