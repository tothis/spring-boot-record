package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author 李磊
 * @since 1.0
 */
@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_A = "exchange-a";
    public static final String EXCHANGE_B = "exchange-b";
    public static final String QUEUE_A = "queue-a";
    public static final String QUEUE_B = "queue-b";
    public static final String QUEUE_C = "queue-c";
    public static final String ROUTING_KEY_A = "routing-key-A";
    public static final String ROUTING_KEY_B = "routing-key-B";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";

    /**
     * 底层实际为一个信道 如每个生产者都配置不同的回调函数 只有第一个生产者生效
     * 其它生产者声明回调函数时直接报错 其它生产者不声明回调函数时时 则使用第一个生产者回调函数
     * <p>
     * 把RabbitTemplate声明为多例可解决此问题
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        return new RabbitTemplate(factory);
    }

    @Bean
    public DirectExchange exchangeA() {
        return new DirectExchange(EXCHANGE_A);
    }

    @Bean
    public DirectExchange exchangeB() {
        return new DirectExchange(EXCHANGE_B);
    }

    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true);
    }

    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, true);
    }

    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C, true);
    }

    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queueA()).to(exchangeA()).with(ROUTING_KEY_A);
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(exchangeB()).with(ROUTING_KEY_B);
    }

    /**
     * 广播交换器
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 绑定所有队列至广播交换器
     */
    @Bean
    public Binding bindingExchangeA(Queue queueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeB(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeC(Queue queueC, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueC).to(fanoutExchange);
    }
}