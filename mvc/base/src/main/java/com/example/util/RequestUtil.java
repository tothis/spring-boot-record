package com.example.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 李磊
 * @datetime 2020/3/6 19:03
 * @description
 */
public class RequestUtil {
    public static HttpServletRequest getRequest() {
        // 获取到当前线程绑定的请求对象
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        // 获取到当前线程绑定的请求对象
        return getRequest().getSession();
    }
}