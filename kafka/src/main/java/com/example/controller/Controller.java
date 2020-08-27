package com.example.controller;

import com.example.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 * @datetime 2019/12/26 11:47
 * @description
 */
@RestController
public class Controller {

    private final String SUCCESS = "success";
    @Autowired
    private Producer producer;

    @GetMapping
    public String send() {
        producer.send();
        return SUCCESS;
    }
}