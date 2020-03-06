package com.example.type;

import com.example.i18n.AppLocaleResolver;
import com.example.util.RequestUtil;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HttpState {
    OK(200, "ok", "请求已经成功处理"),
    NOT_FOUND(404, "not found", "资源未找到"),
    FAIL(500, "internal server error", "服务器内部错误");

    private long code;
    private String us;
    private String cn;

    HttpState(long code, String us, String cn) {
        this.code = code;
        this.us = us;
        this.cn = cn;
    }

    public long code() {
        return code;
    }

    public String us() {
        return us;
    }

    public String cn() {
        return cn;
    }

    public String message() {
        String language = (String) RequestUtil.getSession()
                .getAttribute(AppLocaleResolver.LANGUAGE);
        if (language == null)
            return cn;
        switch (language) {
            case "en_US":
                return us;
            default:
                return cn;
        }
    }

    // long到enum的转换函数
    public static HttpState valueOf(long code) {
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
    @JsonValue
    public String toString() {
        return "{code=" + code + ", us='" + us
                + '\'' + ", cn='" + cn + '\'' + '}';
    }
}