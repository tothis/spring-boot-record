package com.example.producer;

import com.example.constant.KafkaConstant;
import com.example.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * @author 李磊
 */
@Slf4j
@Component
public class Producer {

    private final KafkaTemplate<String, Message<Integer>> kafkaTemplate;

    Producer(final KafkaTemplate<String, Message<Integer>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送消息
     *
     * @return 发送信息
     */
    public String send() {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            final Message<Integer> message = new Message<>();
            message.setType(Message.Type.USER);
            message.setData(i);
            final ListenableFuture<SendResult<String, Message<Integer>>> future = kafkaTemplate.send(KafkaConstant.TEST, message);
            // 异步回调
            future.addCallback(
                    ok -> log.info("生产成功 [{}] [{}]", message, ok)
                    , fail -> log.info("生产失败 [{}] [{}]", message, fail)
            );
            try {
                future.get();
                result.append("生产成功 ");
            } catch (InterruptedException e) {
                result.append("生产失败 ");
                e.printStackTrace();
            } catch (ExecutionException e) {
                result.append("生产失败 ");
                e.printStackTrace();
            }
            result.append(message).append("<br>");
        }
        return result.toString();
    }
}
