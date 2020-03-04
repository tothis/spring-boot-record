package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 * @datetime 2020/3/3 23:52
 * @description
 */

/**
 * @ApiIgnore swagger忽略 可作用类 方法 方法参数
 */
@RequestMapping("admin")
@RestController
public class AdminController {
    @GetMapping
    public String admin() {
        return "admin";
    }

    // @ApiIgnore
    @GetMapping("ignore")
    public String ignore(/*@ApiIgnore */String ignore) {
        return "ignore";
    }
}