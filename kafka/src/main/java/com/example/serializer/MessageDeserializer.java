package com.example.serializer;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Message;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author 李磊
 */
public class MessageDeserializer implements Deserializer<Message> {

    @Override
    public Message deserialize(final String topic, final byte[] data) {
        return ObjectUtil.deserialize(data);
    }
}
