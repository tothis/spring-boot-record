package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.type.DataSourceEnum;
import com.example.type.DataSourceType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 */
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("db1/{id}")
    public User db1(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DataSourceType(DataSourceEnum.db2)
    @GetMapping("db2/{id}")
    public User db2(@PathVariable Long id) {
        return userService.findById(id);
    }
}
