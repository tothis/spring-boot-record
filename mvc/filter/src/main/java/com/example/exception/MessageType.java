package com.example.exception;

import lombok.Getter;

/**
 * 消息类型
 *
 * @author 李磊
 * @since 1.0
 */
@Getter
public enum MessageType {
    OK(0, "请求成功"),
    SYSTEM_ERROR(-1, "系统错误"),

    /* 用户相关 */
    USER_TOKEN_BLANK(100_100_101, "token为空"),
    USER_TOKEN_INVALID(100_100_102, "token过期或失效");

    private final long code;
    private final String message;

    MessageType(long code, String message) {
        this.code = code;
        this.message = message;
    }
}