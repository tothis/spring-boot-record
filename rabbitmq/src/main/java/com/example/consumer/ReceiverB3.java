package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.config.RabbitMQConfig.QUEUE_B;

/**
 * 处理队列B消息
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
@RabbitListener(queues = QUEUE_B)
public class ReceiverB3 {

    @RabbitHandler
    public void process(String content) {
        log.info("B3接收消息 : " + content);
    }
}