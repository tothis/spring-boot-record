package com.example.controller;

import com.example.model.HttpState;
import com.example.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ShiroException.class)
    public Result handleShiroException(ShiroException e) {
        log.error("shiro执行出错 {}", e.getClass().getSimpleName());
        return Result.fail("鉴权或授权过程出错");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public Result page401() {
        return new Result(HttpState.UNAUTHORIZED.code(), "用户未登录");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result page403() {
        return new Result(HttpState.FORBIDDEN.code(), "用户没有访问权限");
    }
}