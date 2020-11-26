package com.example.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * servlet工具类
 *
 * @author 李磊
 * @since 1.0
 */
@UtilityClass
public class ServletUtil {

    public ServletRequestAttributes requestAttributes() {
        // 获取到当前线程绑定的请求对象
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public HttpServletRequest request() {
        // 获取到当前线程绑定的请求对象
        return requestAttributes().getRequest();
    }

    public HttpServletResponse response() {
        return requestAttributes().getResponse();
    }

    public HttpSession session() {
        return request().getSession();
    }

    public <T> T sessionAttr(String name) {
        return (T) session().getAttribute(name);
    }

    public boolean isAjax() {
        return isAjax(request());
    }

    public boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public <T> void write(T data) throws IOException {
        String content = JsonUtil.toJson(data);
        HttpServletResponse response = response();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 防止中文乱码
        response.setCharacterEncoding("utf8");
        response.getWriter().write(content);
    }
}