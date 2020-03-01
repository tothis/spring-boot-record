package com.example.type;

import com.example.i18n.AppLocaleResolver;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HttpState {
    OK(200, "ok", "请求已经成功处理"),
    NOT_FOUND(404, "not found", "资源未找到"),
    FAIL(500, "internal server error", "服务器内部错误");

    private int code;
    private String us;
    private String cn;

    HttpState(int code, String us, String cn) {
        this.code = code;
        this.us = us;
        this.cn = cn;
    }

    public int code() {
        return code;
    }

    public String us() {
        return us;
    }

    public String cn() {
        return cn;
    }

    // 获取到当前线程绑定的请求对象
    private HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    public String message() {
        String language = (String) request.getSession().getAttribute(AppLocaleResolver.LANGUAGE);
        if (language == null)
            return cn;
        switch (language) {
            case "en_US":
                return us;
            default:
                return cn;
        }
    }

    // int到enum的转换函数
    public static HttpState valueOf(int code) {
        //// 枚举值较少时可以使用switch
        //switch (code) {
        //    case 100:
        //        return CONTINUE;
        //    case 200:
        //        return OK;
        //    default:
        //        return null;
        //}

        for (HttpState httpState : values()) {
            if (httpState.code() == code)
                return httpState;
        }
        throw new RuntimeException("枚举解析错误");
    }

    @Override
    public String toString() {
        return "HttpState{" +
                "code=" + code +
                ", us='" + us + '\'' +
                ", cn='" + cn + '\'' +
                '}';
    }
}