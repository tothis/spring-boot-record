package com.example.consumer;

import com.example.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 处理队列C消息
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_C)
public class ReceiverC {

    @RabbitHandler
    public void process(String content) {
        log.info("C接收消息 : " + content);
    }
}