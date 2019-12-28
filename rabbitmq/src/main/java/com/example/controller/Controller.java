package com.example.controller;

import com.example.producer.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 * @datetime 2019/12/26 10:04
 * @description
 */
@Slf4j
@RequestMapping("rabbitmq")
@RestController
public class Controller {

    @Autowired
    private Producer producer;

    private static final String SUCCESS = "success";

    /**
     * 发送消息到队列A
     *
     * @return
     */
    @GetMapping("senda")
    public String senda() {
        for (int i = 0; i < 10; i++) {
            producer.sendMessageA("这是发送的第" + i + "条消息");
        }
        return SUCCESS;
    }

    /**
     * 发送消息到队列B
     *
     * @return
     */
    @GetMapping("sendb")
    public String sendb() {
        for (int i = 1; i <= 10; i++) {
            producer.sendMessageB("这是发送的第" + i + "条消息");
        }
        return SUCCESS;
    }

    /**
     * 发送消息到所有队列
     */
    @GetMapping("send")
    public String send() {
        for (int i = 1; i <= 10; i++) {
            producer.sendAll("这是发送的第" + i + "条消息");
        }
        return SUCCESS;
    }
}