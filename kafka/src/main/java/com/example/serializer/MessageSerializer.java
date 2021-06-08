package com.example.serializer;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Message;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author 李磊
 */
public class MessageSerializer implements Serializer<Message> {

    @Override
    public byte[] serialize(final String topic, final Message data) {
        return ObjectUtil.serialize(data);
    }
}
