package com.example.exception;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description
 */
public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}