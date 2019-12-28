package com.example.consumer;

import com.example.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 李磊
 * 准备了三个类MsgReceiverB_A MsgReceiverB_B MsgReceiverB_C 来消费队列B当中的消息 消费的顺序是负载均衡的
 * 消费的顺序是无序的 也就是不保证先进来的消息先被消费
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_B)
public class ReceiverB2 {

    @RabbitHandler
    public void process(String content) {
        log.info("处理器B接收处理队列B当中的消息 : " + content);
    }
}