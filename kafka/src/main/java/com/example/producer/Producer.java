package com.example.producer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author 李磊
 * @datetime 2019/12/26 11:39
 * @description
 */
@Slf4j
@Component
public class Producer {

    private final KafkaTemplate kafkaTemplate;

    public Producer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // 发送消息方法
    public void send() {
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setId(System.currentTimeMillis());
            message.setMsg(UUID.randomUUID().toString().replace("-", "") + "-" + i);
            message.setSendTime(new Date());
            log.info("发送消息 [{}]", message.toString());
            kafkaTemplate.send("test", message.toString());
        }
    }

    @Data
    static class Message {
        private Long id;
        private String msg;
        private Date sendTime;
    }
}