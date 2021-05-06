package com.example.producer;

import com.example.util.RabbitMQUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.example.config.RabbitMQConfig.*;

/**
 * @author 李磊
 */
@Slf4j
@Component
public class ProducerB implements RabbitTemplate.ConfirmCallback {

    private final RabbitTemplate rabbitTemplate;

    public ProducerB(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置回调函数
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMessageA(String content) {
        rabbitTemplate.convertAndSend(EXCHANGE_A, ROUTING_KEY_A
                , content, RabbitMQUtil.id());
    }

    public void sendMessageB(String content) {
        rabbitTemplate.convertAndSend(EXCHANGE_B, ROUTING_KEY_B
                , content, RabbitMQUtil.id());
    }

    public void sendAll(String content) {
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, null
                , content, RabbitMQUtil.id());
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("B生产者回调id " + correlationData);
        if (ack) {
            log.info("消息成功消费");
        } else {
            log.info("消息消费失败 " + cause);
        }
    }
}