package com.example.controller;

import com.example.util.QuartzUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 * @datetime 2020/3/17 10:48
 * @description
 */
@RestController
public class TestController {

    private boolean flag;

    @GetMapping
    public void index() {
        if (flag = !flag)
            QuartzUtil.start();
        else
            QuartzUtil.standby();
    }
}