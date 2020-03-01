package com.example.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Result<T> {

    private int code;

    private String message;

    private T data;

    // 成功
    public static Result success() {
        return new Result() {{
            setCode(HttpState.OK.code());
            setMessage(HttpState.OK.message());
        }};
    }

    // 成功 返回'success'信息 返回自定义数据
    public static <T> Result<T> success(T data) {
        return new Result() {{
            setCode(HttpState.OK.code());
            setMessage(HttpState.OK.message());
            setData(data);
        }};
    }

    // 失败
    public static Result fail() {
        return new Result() {{
            setCode(HttpState.FAIL.code());
            setMessage(HttpState.FAIL.message());
        }};
    }

    // 失败 返回自定义错误信息
    public static Result fail(String message) {
        return new Result() {{
            setCode(HttpState.FAIL.code());
            setMessage(message);
        }};
    }
}