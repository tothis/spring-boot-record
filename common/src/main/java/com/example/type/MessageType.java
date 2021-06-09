package com.example.type;

import lombok.Getter;

/**
 * 消息类型
 *
 * @author 李磊
 */
@Getter
public enum MessageType {
    OK(0, "请求成功"),
    SYSTEM_ERROR(-1, "系统错误");

    private final long code;
    private final String message;

    MessageType(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
