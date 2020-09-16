package com.example.controller;

import com.example.model.User;
import com.example.runner.DataSourceRunner;
import com.example.service.UserService;
import com.example.type.DataSourceType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 * @datetime 2020/03/18 22:16
 * @description
 */
@RestController
public class UserController {

    private final DataSourceRunner dataSourceRunner;

    private final UserService userService;

    public UserController(DataSourceRunner dataSourceRunner, UserService userService) {
        this.dataSourceRunner = dataSourceRunner;
        this.userService = userService;
    }

    @DataSourceType("db1")
    @GetMapping("db1/{id}")
    public User db1(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DataSourceType("db2")
    @GetMapping("db2/{id}")
    public User db2(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DataSourceType
    @GetMapping("reload/{id}")
    public boolean reload(@PathVariable Long id) {
        userService.findById(id);
        return dataSourceRunner.dataSourceTask();
    }
}