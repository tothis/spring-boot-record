package com.example.util;

import cn.hutool.json.JSONUtil;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet 工具类
 *
 * @author 李磊
 */
@UtilityClass
public class ServletUtil {
    public ServletRequestAttributes requestAttributes() {
        // 获取到当前线程绑定的请求对象
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public HttpServletRequest request() {
        return requestAttributes().getRequest();
    }

    public HttpServletResponse response() {
        return requestAttributes().getResponse();
    }

    public <T> void write(T data) throws IOException {
        String content = JSONUtil.toJsonStr(data);
        HttpServletResponse response = response();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 防止中文乱码
        response.setCharacterEncoding("utf8");
        response.getWriter().write(content);
    }
}
