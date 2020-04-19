package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 李磊
 * @datetime 2020/1/12 15:40
 * @description 基本controller类可被继承
 */
@RequestMapping("base1")
@Controller
public interface BaseInterface {
    @ResponseBody
    @GetMapping("test")
    default String base() {
        return "interface";
    }
}