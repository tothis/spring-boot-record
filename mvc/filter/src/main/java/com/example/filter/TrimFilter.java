package com.example.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

;

/**
 * 参数去除前后空格
 *
 * @author 李磊
 * @since 1.0
 */
@ConditionalOnProperty(value = "lilei.trim.enabled", havingValue = "true")
@Slf4j
@Component
public class TrimFilter extends OncePerRequestFilter implements Ordered {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();
        if (method.equals(HttpMethod.POST.name()) || method.equals(HttpMethod.PUT.name())) {
            ParameterRequestWrapper paramsRequest = new ParameterRequestWrapper(request);
            chain.doFilter(paramsRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public int getOrder() {
        log.info("trim启动");
        return 2;
    }

    class ParameterRequestWrapper extends HttpServletRequestWrapper {

        private Map<String, String[]> params = new HashMap<>();

        public ParameterRequestWrapper(HttpServletRequest request) {
            super(request);
            Map<String, String[]> requestMap = request.getParameterMap();
            log.info("param转化前参数 {}", JsonUtil.toJson(requestMap));
            this.params.putAll(requestMap);
            trimParam(this.params);
            log.info("param转化后参数 {}", JsonUtil.toJson(params));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            // 为空则直接返回
            String json = IoUtil.read(super.getInputStream(), StandardCharsets.UTF_8);
            if (StrUtil.isEmpty(json)) {
                return super.getInputStream();
            }
            log.info("body转化前参数 {}", json);
            Map<String, Object> map = trimBody(json);
            log.info("body转化后参数 {}", JsonUtil.toJson(map));
            ByteArrayInputStream in = new ByteArrayInputStream(JsonUtil.toJson(map).getBytes());
            return new ParameterServletInputStream(in);
        }

        class ParameterServletInputStream extends ServletInputStream {

            private final ByteArrayInputStream inputStream;

            public ParameterServletInputStream(ByteArrayInputStream inputStream) {
                this.inputStream = inputStream;
            }

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() {
                return inputStream.read();
            }
        }
    }

    /**
     * 去除application/x-www-form-urlencoded参数空格
     *
     * @param params
     */
    private static void trimParam(Map<String, String[]> params) {
        Set<String> set = params.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }
            params.put(key, values);
        }
    }

    /**
     * 去除application/json参数空格
     *
     * @param body
     * @return
     */
    private static Map<String, Object> trimBody(String body) {
        JSONObject jsonObject = JSONUtil.parseObj(body);
        Map<String, Object> map = new HashMap<>(jsonObject.size());
        for (Object k : jsonObject.keySet()) {
            Object o = jsonObject.get(k);
            if (o instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<>();
                Iterator<Object> iterator = ((JSONArray) o).iterator();
                while (iterator.hasNext()) {
                    Object obj = iterator.next();
                    list.add(trimBody(obj.toString()));
                }
                map.put(k.toString(), list);
            } else if (o instanceof JSONObject) {
                // 为json对象则继续解析
                map.put(k.toString(), trimBody(o.toString()));
            } else {
                // 不为json对象则放入map
                if (o instanceof String) {
                    map.put(k.toString(), o.toString().trim());
                } else {
                    map.put(k.toString(), o);
                }
            }
        }
        return map;
    }
}