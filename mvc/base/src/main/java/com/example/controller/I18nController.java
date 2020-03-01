package com.example.controller;

import com.example.util.I18nUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 李磊
 * @datetime 2020/2/28 16:53
 * @description
 */
@RequestMapping("i18n")
@Controller
public class I18nController {

    @GetMapping
    public String page() {
        I18nUtil.getMessage("author");
        return "i18n";
    }
}