package com.example.handler;

import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录失败入口
 *
 * @author 李磊
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException e) {
        // if (ServletUtil.isAjax(request)) {
        response.setContentType("application/json;charset=utf8");
        response.getWriter().write("未登录 " + e.getMessage());
        // } else {
        //     response.sendRedirect("/login");
        // }
    }
}