package com.example.consumer;

import com.example.constant.KafkaConstant;
import com.example.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 李磊
 */
@Slf4j
@Component
public class Receiver {
    /**
     * ./kafka-console-producer.sh --broker-list localhost:9092 --topic test
     */
    @KafkaListener(topics = KafkaConstant.TEST)
    /*void listener(final Message<Integer> r, final Acknowledgment ack) {
        log.info("消费 [{}]", r);
        // 发送 ACK
        ack.acknowledge();
    }*/
    @KafkaListener(topics = {"test"})
    void listen(ConsumerRecord<String, Message<Integer>> record, final Acknowledgment ack) {
        Optional<Message<Integer>> kafkaMessage = Optional.ofNullable(record.value());
        // Kafka send 未实现 Serializable 的实体 此处会接收到空的 Optional
        if (kafkaMessage.isPresent()) {
            Message<Integer> r = kafkaMessage.get();
            log.info("消费 [{}]", r);
            // 发送 ACK
            ack.acknowledge();
        }
    }
}
