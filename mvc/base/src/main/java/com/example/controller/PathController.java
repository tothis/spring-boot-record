package com.example.controller;

import com.example.util.HttpUtil;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @datetime 2020/2/27 19:29
 * @description
 */
@RestController
public class PathController {

    private final RequestMappingHandlerMapping mapping;

    public PathController(RequestMappingHandlerMapping mapping) {
        this.mapping = mapping;
    }

    /*@GetMapping("test")
    public String test() {
        return "test";
    }*/

    // 拦截test下的所有子路径 但会被test test/xxxx等路径替代
    @GetMapping("test/**")
    public List test(HttpServletRequest request) {
        String param = HttpUtil.param(request.getParameterMap());
        String hostName = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        Object pathName1 = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String pathName2 = request.getRequestURI();
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
            urlMap.put("requestURL", info.getPatternsCondition().getPatterns()); // url
            urlMap.put("requestMethod", info.getMethodsCondition().getMethods()); // 请求方法
            urlMap.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            urlMap.put("methodName", method.getMethod().getName()); // 方法名
            urlList.add(urlMap);
        }
        return urlList;
    }
}