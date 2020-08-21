package com.example.producer;

import com.example.config.RabbitMQConfig;
import com.example.util.RabbitMQUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
public class ProducerB implements RabbitTemplate.ConfirmCallback {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置回调函数
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMessageA(String content) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_A, RabbitMQConfig.ROUTING_KEY_A
                , content, RabbitMQUtil.id());
    }

    public void sendMessageB(String content) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_B, RabbitMQConfig.ROUTING_KEY_B
                , content, RabbitMQUtil.id());
    }

    public void sendAll(String content) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, null
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