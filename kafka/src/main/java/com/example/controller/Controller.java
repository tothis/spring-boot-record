package com.example.controller;

import com.example.producer.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 */
@RestController
public class Controller {

    private final Producer producer;

    Controller(final Producer producer) {
        this.producer = producer;
    }

    @GetMapping
    String send() {
        return producer.send();
    }
}
