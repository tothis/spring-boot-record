package com.example.consumer;

import com.example.User;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static com.example.config.DelayConfig.QUEUE_DEAD;

/**
 * 处理死信队列消息
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
@RabbitListener(queues = QUEUE_DEAD)
public class DelayReceiver {

    @RabbitHandler
    public void process(User content, @Headers Map headers
            , Channel channel) throws IOException {
        log.info("接收死信队列消息 : " + content);
        // 手动ack
        long deliveryTag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        channel.basicAck(deliveryTag, false);
    }
}