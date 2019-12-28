package com.example.consumer;

import com.example.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 李磊
 * 处理队列C当中的消息
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_C)
public class ReceiverC {

    @RabbitHandler
    public void process(String content) {
        log.info("接收处理队列C当中的消息 : " + content);
    }
}