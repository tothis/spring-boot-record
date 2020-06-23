package com.example.handler;

import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description 自定义注销成功后处理器
 */
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    @SneakyThrows
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) {
        System.out.println(request.getHeader("referer"));
        super.onLogoutSuccess(request, response, authentication);
    }
}