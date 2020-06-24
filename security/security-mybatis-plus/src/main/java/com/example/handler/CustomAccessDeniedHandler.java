package com.example.handler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description 自定义权限不足处理器
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @SneakyThrows
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response
            , AccessDeniedException e) {
        response.setContentType("application/json;charset=utf8");
        response.getWriter().write("无权限 " + e.getMessage());
    }
}