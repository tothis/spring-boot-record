package com.example.controller;

import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.List;

/**
 * @author 李磊
 */
@RestControllerAdvice
public class GlobalExceptionHandle {
    /**
     * 校验Controller方法实体参数
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String paramException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder message = new StringBuilder("校验实体参数");
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> message.append(' ' + error.getField()
                    + "->" + error.getDefaultMessage()));
        }
        return message.toString();
    }

    @ExceptionHandler(ValidationException.class)
    public String constraintException(ValidationException e) {
        return "校验方法参数 " + e.getMessage();
    }

    /**
     * 参数类型转换错误
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public String parameterTypeException(HttpMessageConversionException exception) {
        return "参数类型转换 " + exception.getMessage();
    }
}