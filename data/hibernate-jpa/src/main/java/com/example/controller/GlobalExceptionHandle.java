package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/17 10:05
 * @description
 */
@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String validHandle(BindException exception) {
        BindingResult result = exception.getBindingResult();
        StringBuilder message = new StringBuilder();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> message.append("属性 -> {"
                    + error.getField() + "} 错误信息 -> {" + error.getDefaultMessage() + '}'));
        }
        return message.toString();
    }

    /**
     * 参数类型转换错误
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public String parameterTypeException(HttpMessageConversionException exception) {
        return exception.getCause().getLocalizedMessage();
    }
}