package com.example.controller;

import com.example.producer.DelayProducer;
import com.example.producer.ProducerA;
import com.example.producer.ProducerB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@RestController
public class TestController {

    private final ProducerA producerA;
    private final ProducerB producerB;
    private final DelayProducer delayProducer;

    public TestController(ProducerA producerA, ProducerB producerB
            , DelayProducer delayProducer) {
        this.producerA = producerA;
        this.producerB = producerB;
        this.delayProducer = delayProducer;
    }

    @GetMapping
    public ResponseEntity send() {
        for (int i = 0; i < 10; i++) {
            // 发送消息到队列A
            producerB.sendMessageA("A生产者-a队列消息" + i);
            // 发送消息到队列B
            // producerB.sendMessageB("A生产者-b队列消息" + i);
            // 发送消息到所有队列
            // producerA.sendAll("A生产者消息" + i);
            // producerB.sendAll("B生产者消息" + i);
            // 发送消息到延时队列
            // delayProducer.send(new User(0L, "李磊" + i), 5);
        }
        return ResponseEntity.ok(null);
    }
}