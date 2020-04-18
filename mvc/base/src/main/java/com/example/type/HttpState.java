package com.example.type;

import com.example.i18n.AppLocaleResolver;
import com.example.util.ServletUtil;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考
 * org.springframework.http.HttpStatus
 * https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status
 */
@Slf4j
public enum HttpState {
    CONTINUE(100, "继续"),
    SWITCHING_PROTOCOLS(101, "协议切换"),
    PROCESSING(102, "处理中"),
    CHECKPOINT(103, "检查站"),
    OK(200, "成功"),
    CREATED(201, "已建立"),
    ACCEPTED(202, "已认可"),
    NON_AUTHORITATIVE_INFORMATION(203, "非权威信息"),
    NO_CONTENT(204, "无内容"),
    RESET_CONTENT(205, "重设内容"),
    PARTIAL_CONTENT(206, "部分内容"),
    MULTI_STATUS(207, "多状态"),
    ALREADY_REPORTED(208, "已报告"),
    IM_USED(226, "已使用"),
    MULTIPLE_CHOICES(300, "多项选择"),
    MOVED_PERMANENTLY(301, "永久移动"),
    FOUND(302, "暂时移动"),

    SEE_OTHER(303, "查看其他"),
    NOT_MODIFIED(304, "未修改"),
    TEMPORARY_REDIRECT(307, "临时重定向"),
    PERMANENT_REDIRECT(308, "永久重定向"),
    BAD_REQUEST(400, "请求错误"),
    UNAUTHORIZED(401, "未经授权"),
    PAYMENT_REQUIRED(402, "需要付款"),
    FORBIDDEN(403, "被阻止"),
    NOT_FOUND(404, "未找到"),
    METHOD_NOT_ALLOWED(405, "不允许使用此方法"),
    NOT_ACCEPTABLE(406, "不能接受"),
    PROXY_AUTHENTICATION_REQUIRED(407, "需要代理身份验证"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "冲突"),
    GONE(410, "不可用"),
    LENGTH_REQUIRED(411, "Content-Length未定义"),
    PRECONDITION_FAILED(412, "前提条件失败"),
    PAYLOAD_TOO_LARGE(413, "有效负载过大"),
    URI_TOO_LONG(414, "URI太长"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "要求的范围无法满足"),
    EXPECTATION_FAILED(417, "Expect预期失败"),
    I_AM_A_TEAPOT(418, "我是茶壶"),
    UNPROCESSABLE_ENTITY(422, "不可处理实体"),
    LOCKED(423, "已锁定"),
    FAILED_DEPENDENCY(424, "依赖失败"),
    TOO_EARLY(425, "太早了"),
    UPGRADE_REQUIRED(426, "需要升级"),
    PRECONDITION_REQUIRED(428, "需前提条件"),
    TOO_MANY_REQUESTS(429, "请求过多"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "请求标头字段太大"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "由于法律原因而无法使用"),
    INTERNAL_SERVER_ERROR(500, "内部服务器错误"),
    NOT_IMPLEMENTED(501, "未实现"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "暂停服务"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "不支持的HTTP版本"),
    VARIANT_ALSO_NEGOTIATES(506, "服务器配置错误"),
    INSUFFICIENT_STORAGE(507, "存储空间不足"),
    LOOP_DETECTED(508, "检测到循环"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "超出带宽限制"),
    NOT_EXTENDED(510, "不扩展"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "需要网络验证");

    // 声明为final防止被更改
    private final long code;
    private final String message;

    HttpState(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long code() {
        log.info("666");
        return code;
    }

    public String message() {
        return message;
    }

    // long到enum的转换函数
    public static HttpState valueOf(long code) {
//        // 枚举值较少时可以使用switch
//        switch (code) {
//            case 100:
//                return CONTINUE;
//            case 200:
//                return OK;
//            default:
//                return null;
//        }

        for (HttpState httpState : values()) {
            if (httpState.code() == code)
                return httpState;
        }
        throw new RuntimeException("解析错误");
    }

    public String value() {
        String language = ServletUtil.sessionAttr(AppLocaleResolver.LANGUAGE);
        if (language == null)
            return message;
        switch (language) {
            case "en_US":
                return this.name().toLowerCase();
            default:
                return message;
        }
    }

    @Override
    @JsonValue
    public String toString() {
        return "HttpState{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}