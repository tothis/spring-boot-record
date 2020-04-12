package com.example.controller;

import org.apache.shiro.authz.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test1")
@RestController
public class Test1Controller {

    @GetMapping
    public String noAuth() {
        return "免权限";
    }

    // 用户未登录时(即游客) subject.getPrincipal() == null 接口可访问
    // 用户登录后 subject.getPrincipal() != null 接口不可访问 并抛出UnauthenticatedException异常
    // config中设置所有接口`authc`后@RequiresGuest注解失效 把此接口设为`anon`后可解决此问题
    @RequiresGuest
    @GetMapping("guest")
    public String guest() {
        return "游客";
    }

    // 已登录用户可访问 比@RequiresUser严格 用户未登录调用此接口 抛出UnauthenticatedException
    @RequiresAuthentication
    @GetMapping("auth")
    public String auth() {
        return "已登录";
    }

    // 已登录用户或`记住我`用户可访问 用户未登录或不是`记住我`用户调用此接口 抛出UnauthenticatedException
    @RequiresUser
    @GetMapping("user")
    public String user() {
        return "@RequiresUser";
    }

    // 登录用户具有view权限可访问 未登录 抛出UnauthenticatedException
    @RequiresPermissions("view")
    @GetMapping("view")
    public String view() {
        return "view权限";
    }

    // 登录用户具有view权限可访问 未登录 抛出UnauthenticatedException
    @RequiresPermissions("edit")
    @GetMapping("edit")
    public String edit() {
        return "edit权限";
    }

    // 登录用户具有admin角色可访问 已登录 无此角色 抛出UnauthorizedException 未登录 抛出UnauthenticatedException
    @RequiresRoles("staff")
    @GetMapping("staff")
    public String staff() {
        return "staff角色";
    }

    @RequiresRoles("admin")
    @GetMapping("admin")
    public String js() {
        return "admin角色";
    }
}