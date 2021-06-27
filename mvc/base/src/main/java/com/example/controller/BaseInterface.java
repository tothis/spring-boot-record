package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础Controller
 *
 * @author 李磊
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
