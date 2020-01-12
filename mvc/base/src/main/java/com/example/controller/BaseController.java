package com.example.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;

/**
 * @author 李磊
 * @datetime 2020/1/12 15:40
 * @description
 */
public class BaseController {

    @ModelAttribute
    // 设置name属性 key为string 直接返回类 相当于把类名转驼峰作为键名
    private String id(String id) {
        return id;
    }

    // 设置name属性 key为name
    @ModelAttribute
    private void data(Model model, String name) {
        model.addAttribute("name", name);
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("data", new HashMap() {{
            for (int i = 0; i < 100; i++) {
                put(i, i);
            }
        }});
        return "index";
    }
}