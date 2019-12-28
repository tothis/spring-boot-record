package com.example.producer;

import com.example.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author 李磊
 * @datetime 2019/12/25 10:02
 * @description
 */
@Slf4j
@Component
public class Producer implements RabbitTemplate.ConfirmCallback {

    // 由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE 所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // rabbitTemplate如果为单例的话 那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMessageA(String content) {
        // CorrelationData 该数据的作用是给每条消息一个唯一的标识
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        // 把消息放入ROUTINGKEY_A对应的队列当中去 对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_A, RabbitMQConfig.ROUTINGKEY_A, content, correlationId);
    }

    public void sendMessageB(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_B, RabbitMQConfig.ROUTINGKEY_B, content, correlationId);
    }

    /**
     * 消息发送 这里不设置routing_key 因为设置了也无效 发送端的routing_key写任何字符都会被忽略
     *
     * @param content
     */
    public void sendAll(String content) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", content);
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("回调id : " + correlationData);
        if (ack) {
            log.info("消息成功消费");
        } else {
            log.info("消息消费失败 : " + cause);
        }
    }
}