package com.example.exception;

/**
 * @author 李磊
 */
public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}