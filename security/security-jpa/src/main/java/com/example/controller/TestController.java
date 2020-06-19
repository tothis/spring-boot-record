package com.example.controller;

import com.example.model.User;
import com.example.util.UserUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 * @datetime 2020/4/16 12:56
 * @description
 */
@RestController
public class TestController extends BaseController {

    @GetMapping("test")
    public User test() {
        super.authentication();
        return UserUtil.getCurrentUser();
    }
}