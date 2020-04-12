package com.example.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiresAuthentication
@RequestMapping("test2")
@RestController
public class Test2Controller {

    // 当前类使用@RequiresAuthentication注解 此方法上未使用其它shiro注解 则使用类注解做访问控制
    @GetMapping
    public String hello() {
        return "已经登录";
    }

    // 当前类使用@RequiresAuthentication注解 此方法要求游客可访问 产生矛盾
    // 因此无论是用户是否登录 都会抛出UnauthenticatedException
    @RequiresGuest
    @GetMapping("guest")
    public String guest() {
        return "游客";
    }
}