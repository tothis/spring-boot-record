package com.example.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.constant.FilterConstant;
import com.example.entity.Result;
import com.example.type.MessageType;
import com.example.util.JsonUtil;
import com.example.util.ServletUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名过滤器
 *
 * @author 李磊
 */
@ConditionalOnProperty(value = "lilei.sign.enabled", havingValue = "true")
@Slf4j
@Component
public class SignFilter extends OncePerRequestFilter implements Ordered {

    @Value("${lilei.sign.salt}")
    private String salt;

    /**
     * URL 参数转 map
     */
    public static Map<String, String[]> urlToMap(String content) {
        Map<String, String[]> result = new HashMap<>();
        String[] split = content.split("&");
        for (String item : split) {
            int index = item.indexOf("=");
            String key = item.substring(0, index);
            if (result.containsKey(key)) {
                String[] params = result.get(key);
                String[] newParams = new String[params.length + 1];
                System.arraycopy(params, 0, newParams, 0, params.length);
                newParams[newParams.length] = item.substring(index + 1);
                result.put(key, newParams);
            } else {
                result.put(key, new String[]{item.substring(index + 1)});
            }
        }
        return result;
    }

    /**
     * multipart form 参数转 map
     */
    private static Map<String, Object> multipartFormToMap(HttpServletRequest request) throws ServletException, IOException {
        final Collection<Part> parts = request.getParts();
        final Map<String, Object> result = MapUtil.newHashMap(parts.size());
        for (final Part part : parts) {
            // 不记录文件参数
            if (part.getContentType() == null) {
                try (final InputStream in = part.getInputStream()) {
                    result.put(part.getName(), IoUtil.readObj(in));
                }
            }
        }
        return result;
    }

    /**
     * body 参数转 map
     */
    public static Map<String, Object> bodyToMap(String content) {
        return JsonUtil.toObject(content, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * map 参数转字符串
     */
    public static String mapToStr(Map<String, Object> param) {
        if (MapUtil.isEmpty(param)) {
            return "";
        }
        final TreeMap<String, Object> sort = MapUtil.sort(param);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : sort.entrySet()) {
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        removeLastChar(builder);
        return builder.toString();
    }

    /**
     * 参数转字符串
     *
     * @param param -
     * @return -
     */
    private static String paramToStr(Map<String, String[]> param) {
        if (MapUtil.isEmpty(param)) {
            return "";
        }
        // 参数排序
        TreeMap<String, String[]> sort = MapUtil.sort(param);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String[]> entry : sort.entrySet()) {
            for (String item : entry.getValue()) {
                builder.append(entry.getKey() + "=" + item + "&");
            }
        }
        removeLastChar(builder);
        return builder.toString();
    }

    /**
     * 删除最后一个字符
     *
     * @param builder -
     * @return -
     */
    private static void removeLastChar(StringBuilder builder) {
        builder.deleteCharAt(builder.length() - 1);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        // 读取请求体中数据(字符串形式) 同一个流不可读取多次 使用 HttpServletRequestWrapper 解决
        HttpServletRequest requestWrapper = new BodyServletRequestWrapper(request);
        if (sign(requestWrapper)) {
            chain.doFilter(requestWrapper, response);
        } else {
            ServletUtil.write(new Result(MessageType.SYSTEM_ERROR));
        }
    }

    /**
     * 签名校验
     */
    private boolean sign(HttpServletRequest request) throws IOException, ServletException {
        String sign = request.getHeader(FilterConstant.SING_HEADER);
        String type = request.getContentType();
        String param = null;

        if (type == null) {
            // URL 参数
            param = paramToStr(urlToMap(request.getQueryString()));
        }
        // form 表单参数
        else if (type.equals(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            param = paramToStr(request.getParameterMap());
        }
        // multipart form
        else if (type.indexOf(MediaType.MULTIPART_FORM_DATA_VALUE) > -1) {
            param = mapToStr(multipartFormToMap(request));
        }
        // JSON 表单参数
        else if (type.indexOf(MediaType.APPLICATION_JSON_VALUE) > -1) {
            try (final ServletInputStream in = request.getInputStream()) {
                param = mapToStr(bodyToMap(IoUtil.read(in, StandardCharsets.UTF_8)));
            }
        }
        // 接口无参数 不进行接口校验
        if (StrUtil.isBlank(param)) {
            return true;
        }
        return SecureUtil.md5(param + salt).equals(sign);
    }

    @Override
    public int getOrder() {
        log.info("sign 启动");
        return FilterConstant.SING_SORT;
    }
}
