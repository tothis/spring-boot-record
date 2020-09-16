package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * @author 李磊
 * @since 1.0
 */
@SpringBootTest(classes = RabbitMQApplication.class)
public class RabbitAdminTest {

    private RabbitTemplate rabbitTemplate;

    private RabbitAdmin rabbitAdmin;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setRabbitAdmin(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    private String testDirect = "test.direct";
    private String testDirectQueue = "test.direct.queue";
    private String testDirectRoutingKey = "test.direct.routing-key";

    private String testTopic = "test.topic";
    private String testTopicQueue = "test.topic.queue";

    private String testFanout = "test.fanout";
    private String testFanoutQueue = "test.fanout.queue";

    /**
     * 创建交换机 队列并绑定
     */
    @Test
    void test() {
        /** 方式1 */

        // 创建direct类型交换机
        rabbitAdmin.declareExchange(new DirectExchange(testDirect, false, false));
        // 创建队列
        rabbitAdmin.declareQueue(new Queue(testDirectQueue, false));
        // 绑定操作
        rabbitAdmin.declareBinding(new Binding(testDirectQueue,
                Binding.DestinationType.QUEUE,
                testDirect, testDirectRoutingKey, new HashMap<>()));
        /** 方式2 */

        // topic类型交换机
        TopicExchange topicExchange = new TopicExchange(testTopic, false, false);
        Queue topicQueue = new Queue(testTopicQueue, false);
        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareQueue(topicQueue);
        rabbitAdmin.declareBinding(
                BindingBuilder
                        // 直接创建队列
                        .bind(topicQueue)
                        // 直接创建交换机 建立关联关系
                        .to(topicExchange)
                        // 指定路由Key
                        .with("user.#"));

        // fanout类型交换机
        FanoutExchange fanoutExchange = new FanoutExchange(testFanout, false, false);
        Queue fanoutQueue = new Queue(testFanoutQueue, false);
        rabbitAdmin.declareExchange(fanoutExchange);
        rabbitAdmin.declareQueue(fanoutQueue);
        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(fanoutQueue)
                        .to(fanoutExchange));

        // 发送数据
        // rabbitTemplate.convertAndSend(testDirect, testDirectRoutingKey, "1234");
        // 清空队列数据
        rabbitAdmin.purgeQueue(testDirectQueue, false);
    }
}