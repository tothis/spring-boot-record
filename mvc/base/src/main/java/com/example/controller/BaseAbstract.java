package com.example.controller;

import com.example.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author 李磊
 * @datetime 2020/1/12 15:40
 * @description 基本controller类可被继承
 */
@RequestMapping("base2")
@Controller
public abstract class BaseAbstract {
    protected final transient Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /**
     * 每个controller方法执行前被执行
     */
    @ModelAttribute
    private void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * 添加cookie
     */
    protected final void setCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30); // 保留一个月(单位秒)
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     */
    protected final void deleteCookie(String key) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(key)) {
                    Cookie cookie = new Cookie(key, null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0); // 立即过期
                    response.addCookie(cookie);
                }
            }
        }
    }

    /**
     * 获取cookie
     */
    protected final String getCookie(String key) {
        String result = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(key)) {
                try {
                    result = URLDecoder.decode(cookie.getValue(), "UTF-8");
                } catch (Exception e) {
                }
                break;
            }
        }
        return result;
    }

    /**
     * 取得访问项目的url
     */
    protected final String baseUrl() {
        return request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }

    /**
     * ajax输出
     *
     * @param content 输出的文本内容
     * @param type    输出的文本类内容
     */
    protected final void ajax(String content, String type) {
        try {
            response.setContentType(type + ";charset=utf8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 响应json格式的字符串
     *
     * @param content json格式的字符串
     */
    protected final void ajax(String content) {
        ajax(content, "application/json");
    }

    /**
     * 响应带状态的json格式的数据
     *
     * @param status
     * @param content
     */
    protected final void ajaxJson(Object content, long status) {
        String json = "{\"status\" : \"" + status + "\", " +
                "\"message\" : " + JsonUtil.objectToJson(content) + " }";
        ajax(json, "application/json");
    }

    /**
     * 响应json格式的数据
     *
     * @param content
     */
    protected final void ajaxJson(Object content) {
        String json = JsonUtil.objectToJson(content);
        ajax(json, "application/json");
    }

    @ResponseBody
    @GetMapping("test")
    protected String base() {
        return "abstract";
    }
}