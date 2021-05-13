package com.example.controller;

import com.example.producer.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 */
@RestController
public class Controller {

    private final String SUCCESS = "success";
    private final Producer producer;

    public Controller(Producer producer) {
        this.producer = producer;
    }

    @GetMapping
    public String send() {
        producer.send();
        return SUCCESS;
    }
}