package com.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息
 * <p>
 * Kafka send 未实现 Serializable 的实体会被解析为空数据
 *
 * @author 李磊
 */
@Data
public class Message<T> implements Serializable {
    private Type type;
    private T data;

    public enum Type {
        USER, LOG
    }
}
