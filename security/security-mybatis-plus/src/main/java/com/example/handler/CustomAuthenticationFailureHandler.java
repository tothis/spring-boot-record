package com.example.handler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description 自定义登录失败处理器 配置jwt过滤器后失效
 */
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private String defaultFailureUrl;

    public CustomAuthenticationFailureHandler() {
    }

    public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException e) {
        System.out.println("认证失败");
    }
}