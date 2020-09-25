package com.example.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * servlet工具类
 *
 * @author 李磊
 * @since 1.0
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

    public static boolean isAjax() {
        return isAjax(request());
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}