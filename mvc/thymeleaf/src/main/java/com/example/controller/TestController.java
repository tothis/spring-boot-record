package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 李磊
 * @datetime 2019/12/29 0:10
 * @description
 */
@Controller
public class TestController {
    @GetMapping
    public String index() {
        return "index";
    }
}