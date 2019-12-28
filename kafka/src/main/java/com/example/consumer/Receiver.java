package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 李磊
 * @datetime 2019/12/26 11:46
 * @description
 */
@Slf4j
@Component
public class Receiver {
    // ./kafka-console-producer.sh --broker-list localhost:9092 --topic test
    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("record [{}]", record);
            log.info("message [{}]", message);
        }
    }
}