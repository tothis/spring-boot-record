package com.example.util;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @author 李磊
 * @since 1.0
 */
public class RabbitMQUtil {
    public static CorrelationData id() {
        return new CorrelationData(StringUtil.uuid());
    }
}