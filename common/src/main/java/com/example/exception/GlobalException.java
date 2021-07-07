package com.example.exception;

import com.example.type.MessageType;
import lombok.Getter;

/**
 * 全局异常
 *
 * @author 李磊
 */
@Getter
public class GlobalException extends RuntimeException {

    private final int code;

    private final String message;

    public GlobalException(MessageType type) {
        this.code = type.getCode();
        this.message = type.getMessage();
    }
}
