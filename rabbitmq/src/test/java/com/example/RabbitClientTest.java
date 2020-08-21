package com.example;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 李磊
 * @since 1.0
 */
public class RabbitClientTest {

    private static final String QUEUE_NAME = "test-queue";
    private static final String EXCHANGE_NAME = "test-exchange";
    private static final String ROUTING_KEY = "test-routing-key";

    public static void main(String[] args) {
        producer();
        consumer();
    }

    @SneakyThrows
    private static void producer() {
        // 创建连接
        Connection connection = connection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 声明交换器 交换器不存在会自动创建
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明队列 队列不存在会自动创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 通过路由键绑定队列和交换器
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        for (int i = 0; i < 10; i++) {
            String message = "测试信息" + i;
            // 发送信息到指定队列
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY
                    , MessageProperties.PERSISTENT_TEXT_PLAIN
                    , message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产 -> " + message);
        }
        // 关闭信道
        channel.close();
        // 关闭连接
        connection.close();
    }

    @SneakyThrows
    private static void consumer() {
        // 创建连接
        Connection connection = connection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 声明队列 队列不存在会自动创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 设置每个消费者同时只能处理一条消息
        channel.basicQos(1);
        // 声明消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope
                    , AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("消费 -> " + message);
                // 手动进行ACK
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听队列
        // channel.basicConsume(QUEUE_NAME, consumer);
        // 参数二表示是否自动ACK
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    @SneakyThrows
    private static Connection connection() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.92.134");
        // 创建连接
        return connectionFactory.newConnection();
    }
}