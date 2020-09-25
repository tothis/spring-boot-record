package com.example.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.config.RabbitMQConfig.QUEUE_A;

/**
 * 处理队列A消息
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Component
@RabbitListener(queues = QUEUE_A)
public class ReceiverA {

    /**
     * 手动确认消息 假如不确认的话 消息一直会存在在队列当中 下次消费的时候 就会出现重复消费
     *
     * @param content
     * @param channel
     * @param message
     */
    @RabbitHandler
    public void process(String content, Channel channel, Message message) throws IOException {
        log.info("A接收消息 : " + content);
        try {
            // 防止重复消费 可根据传过来的唯一id进行区分
            // 发送成功确认ACK 告诉服务器当前消息已经消费 发送成功当前消费消息会被清除
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            // 发送失败确认ACK 消息删除或重放至队列 参数3 为true 会把消费失败消息重新添加到队列尾端 为false 则删除此信息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    // 最简单的消息消费功能
    // @RabbitHandler
    // public void process(String content) {
    //     log.info("A接收消息 : " + content);
    // }
}