package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 死信队列配置
 *
 * @author 李磊
 * @since 1.0
 */
@Configuration
public class DelayConfig {

    public static final String QUEUE = "queue";
    public static final String EXCHANGE = "exchange";
    public static final String ROUTING_KEY = "routing-key";

    public static final String QUEUE_DEAD = "queue-dead";
    public static final String EXCHANGE_DEAD = "exchange-dead";
    public static final String ROUTING_KEY_DEAD = "routing-key-dead";

    /**
     * 死信队列
     */
    @Bean
    public Queue queueDead() {
        return new Queue(QUEUE_DEAD, true);
    }

    @Bean
    public DirectExchange exchangeDead() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(EXCHANGE_DEAD)
                .durable(true)
                .build();
    }

    @Bean
    public Binding bindingExchangeDead(Queue queueDead, DirectExchange exchangeDead) {
        return BindingBuilder
                .bind(queueDead)
                .to(exchangeDead)
                .with(ROUTING_KEY_DEAD);
    }

    @Bean
    public Queue queue() {
        // 将普通队列绑定到死信队列交换机
        return QueueBuilder
                .durable(QUEUE)
                // 配置到期后转发的交换
                .withArgument("x-dead-letter-exchange", EXCHANGE_DEAD)
                // 配置到期后转发的路由键
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY_DEAD)
                // 延时时间此处可以写死 也可由生产者发布时指定
                // .withArgument("x-message-ttl", 5 * 1000)
                .build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingExchange(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}