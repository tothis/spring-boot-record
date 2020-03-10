package com.example.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/3/10 9:49
 * @description
 */
@RequestMapping("jsonp")
@RestController
public class JsonpController {
    @GetMapping
    public JSONPObject test(String callbackName, String name) {
        @Data
        @AllArgsConstructor
        class User {
            private String name;
        }
        // 返回jackson的JSONPObject对象会解析成js对象的格式
        // name({"data":"xxxx"]})而不是{"name": {"data":"xxxx"}}
        return new JSONPObject(callbackName == null ? "name" : callbackName
                , new HashMap<String, List<User>>() {{
            put("data", new ArrayList<User>() {{
                for (int i = 0; i < 2; i++) {
                    add(new User(name + '-' + i));
                }
            }});
        }});
    }
}