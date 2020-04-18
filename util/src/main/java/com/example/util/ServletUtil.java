package com.example.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 李磊
 * @datetime 2020/3/6 19:03
 * @description
 */
public class ServletUtil {
    public static ServletRequestAttributes requestAttributes() {
        // 获取到当前线程绑定的请求对象
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest request() {
        // 获取到当前线程绑定的请求对象
        return requestAttributes().getRequest();
    }

    public static HttpServletResponse response() {
        return requestAttributes().getResponse();
    }

    public static HttpSession session() {
        return request().getSession();
    }

    public static <T> T sessionAttr(String name) {
        return (T) session().getAttribute(name);
    }
}