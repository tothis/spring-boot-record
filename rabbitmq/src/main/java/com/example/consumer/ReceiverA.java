package com.example.consumer;

import com.example.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 李磊
 * @datetime 2019/12/25 10:11
 * @description
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_A)
public class ReceiverA {

    /**
     * 手动确认消息 假如不确认的话 消息一直会存在在队列当中 下次消费的时候 就会出现重复消费
     *
     * @param content
     * @param channel
     * @param message
     */
    @RabbitHandler
    public void process(String content, Channel channel, Message message) {
        log.info("接收处理队列A当中的消息 : " + content);
        // 告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // 丢弃这条消息
            // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            e.printStackTrace();
        }
    }

    // 最简单的消息消费功能
    // @RabbitHandler
    // public void process(String content) {
    //     log.info("接收处理队列A当中的消息 : " + content);
    // }
}