package com.example.producer;

import com.example.util.RabbitMQUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.example.config.RabbitMQConfig.FANOUT_EXCHANGE;

/**
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
public class ProducerA implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private final RabbitTemplate rabbitTemplate;

    public ProducerA(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 消息发送到exchange回调函数
        this.rabbitTemplate.setConfirmCallback(this::confirm);
        // 消息从exchange投递到queue失败时回调函数
        this.rabbitTemplate.setReturnCallback(this::returnedMessage);
    }

    public void sendAll(String content) {
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, null
                , content, RabbitMQUtil.id());
    }

    /**
     * 消息发送确认 消息投递到exchange回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("A生产者回调id " + correlationData);
        if (ack) {
            log.info("消息成功消费");
        } else {
            log.info("消息消费失败 " + cause);
        }
    }

    /**
     * 消息从交换机成功到达队列 不会被调用此方法
     * 消息从交换机未能成功到达队列 会被调用此方法
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText
            , String exchange, String routingKey) {
        log.info("returnedMessage [消息从交换机到队列失败]  message ：" + message);
    }
}