package com.example.entity;

import com.example.type.MessageType;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一返回数据
 *
 * @author 李磊
 */
@Getter
@Setter
public class Result<T> {

    public static final Result OK;

    static {
        Result r = new Result();
        r.setCode(MessageType.OK.getCode());
        r.setMessage(MessageType.OK.getMessage());
        OK = r;
    }

    private int code;

    private String message;

    private T data;

    public static <T> Result<T> ok(T data) {
        if (data == null) {
            return OK;
        }
        Result<T> r = new Result<>();
        r.setCode(MessageType.OK.getCode());
        r.setMessage(MessageType.OK.getMessage());
        r.setData(data);
        return r;
    }
}
