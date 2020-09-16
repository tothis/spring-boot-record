package com.example.producer;

import com.example.util.RabbitMQUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.example.config.DelayConfig.EXCHANGE;
import static com.example.config.DelayConfig.ROUTING_KEY;

/**
 * 延时队列生产者
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
public class DelayProducer {

    private final RabbitTemplate rabbitTemplate;

    public DelayProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String content, long time) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY
                , content, message -> {
                    // 单位毫秒
                    message.getMessageProperties().setExpiration(String.valueOf(time * 1000));
                    return message;
                }, RabbitMQUtil.id());
    }
}