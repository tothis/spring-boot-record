package com.example.controller;

import com.example.util.HttpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李磊
 */
@RequestMapping("path")
@RestController
public class PathController {

    private final RequestMappingHandlerMapping mapping;

    public PathController(RequestMappingHandlerMapping mapping) {
        this.mapping = mapping;
    }

    @GetMapping("**")
    public List<String> test(HttpServletRequest request) {
        String param = HttpUtil.param(request.getParameterMap());
        String hostName = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        Object pathName1 = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        // request.getRequestURI() 获取的路径包含 nginx 转发前缀
        String pathName2 = request.getServletPath();
        StringBuffer url = request.getRequestURL();
        return new ArrayList() {{
            add(hostName + pathName1 + param);
            add(hostName + pathName2 + param);
            add(url + param);
        }};
    }

    @GetMapping("mapping")
    public List<HashMap<String, Object>> mapping() {
        List<HashMap<String, Object>> urlList = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HashMap<String, Object> urlMap = new HashMap<>();
            RequestMappingInfo info = entry.getKey();
            HandlerMethod method = entry.getValue();
            // URL
            urlMap.put("requestURL", info.getPatternsCondition().getPatterns());
            // 请求方法
            urlMap.put("requestMethod", info.getMethodsCondition().getMethods());
            // 类名
            urlMap.put("className", method.getMethod().getDeclaringClass().getName());
            // 方法名
            urlMap.put("methodName", method.getMethod().getName());
            urlList.add(urlMap);
        }
        return urlList;
    }
}
