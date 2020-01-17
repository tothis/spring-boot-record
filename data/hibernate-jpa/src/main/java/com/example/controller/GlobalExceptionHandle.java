package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * @author 李磊
 * @datetime 2020/1/17 10:05
 * @description
 */
@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(ValidationException exception) {
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<?> item : violations) {
                /** 打印验证不通过的信息 */
                stringBuilder.append(item.getMessage() + "<br>");
            }
            return stringBuilder.toString();
        }
        return "请求失败";
    }
}