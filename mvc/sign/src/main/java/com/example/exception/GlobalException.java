package com.example.exception;

import lombok.Getter;

/**
 * 基础异常
 *
 * @author 李磊
 * @since 1.0
 */
@Getter
public class GlobalException extends RuntimeException {

    private long code;
    private String message;

    public GlobalException(MessageType type) {
        this.code = type.getCode();
        this.message = type.getMessage();
    }
}