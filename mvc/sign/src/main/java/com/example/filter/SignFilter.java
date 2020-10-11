package com.example.filter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.entity.ResultEntity;
import com.example.exception.MessageType;
import com.example.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名过滤器
 *
 * @author 李磊
 * @since 1.0
 */
@ConditionalOnProperty(value = "sign.enabled", havingValue = "true")
@Slf4j
@Component
public class SignFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();
        if (method.equals(HttpMethod.POST.name())
                || method.equals(HttpMethod.PUT.name())) {
            // 读取请求体中数据(字符串形式) 同一个流不可读取多次 使用HttpServletRequestWrapper解决
            HttpServletRequest requestWrapper = new BodyServletRequestWrapper(request);
            if (sign(requestWrapper)) {
                chain.doFilter(requestWrapper, response);
            } else {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                // 防止中文乱码
                response.setCharacterEncoding("utf8");
                response.getWriter().write(JsonUtil.toJson(
                        new ResultEntity() {{
                            setCode(MessageType.SYSTEM_ERROR.getCode());
                            setMessage(MessageType.SYSTEM_ERROR.getMessage());
                        }}
                ));
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Value("${sign.salt}")
    private String salt;

    /**
     * 签名校验
     */
    private boolean sign(HttpServletRequest request) throws IOException {
        String sign = request.getHeader("sign");
        byte[] bytes = new byte[1024];
        int length;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServletInputStream inputStream = request.getInputStream();
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        String contentType = request.getContentType();
        String content = new String(outputStream.toByteArray());
        // 接口无参数 不进行接口校验
        if (StrUtil.isBlank(content)) {
            return true;
        }
        String param = null;
        switch (contentType) {
            case MediaType.APPLICATION_FORM_URLENCODED_VALUE:
                param = param(urlToMap(content));
                break;
            case MediaType.APPLICATION_JSON_VALUE:
                param = param(bodyToMap(content));
                break;
            default:
                break;
        }
        // 接口有参数但无法解析出参数 可能为上传文件 不进行接口校验
        if (StrUtil.isBlank(param)) {
            return true;
        }
        return SecureUtil.md5(param + salt).equals(sign);
    }

    /**
     * URL参数转Map
     */
    public static Map<String, String> urlToMap(String content) {
        Map<String, String> result = new HashMap<>();
        String[] params = content.split("&");
        for (String param : params) {
            int index = param.indexOf("=");
            result.put(param.substring(0, index), param.substring(index + 1));
        }
        return result;
    }

    /**
     * body参数转Map
     */
    public static Map<String, String> bodyToMap(String content) {
        return JsonUtil.toObject(content
                , new TypeReference<Map<String, String>>() {
                });
    }

    /**
     * 参数排序组合
     *
     * @param param
     * @return
     */
    private static String param(Map<String, String> param) {
        if (MapUtil.isEmpty(param)) {
            return "";
        }
        // 参数排序
        TreeMap<String, String> sort = MapUtil.sort(param);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : sort.entrySet()) {
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        // 删除最后一个'&'字符
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    /**
     * 保存流 防止流第二次读取报错
     *
     * @author 李磊
     * @since 1.0
     */
    class BodyServletRequestWrapper extends HttpServletRequestWrapper {

        private final byte[] body;

        public BodyServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            body = StreamUtils.copyToByteArray(request.getInputStream());
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public int read() {
                    return inputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }
            };
        }
    }
}