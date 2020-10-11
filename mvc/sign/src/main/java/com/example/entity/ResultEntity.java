package com.example.entity;

import com.example.exception.MessageType;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一返回数据
 *
 * @author 李磊
 * @since 1.0
 */
@Getter
@Setter
public class ResultEntity<T> {

    public static final ResultEntity OK = ok(null);

    private long code;

    private String message;

    private T data;

    public static <T> ResultEntity<T> ok(T data) {
        ResultEntity<T> r = new ResultEntity<>();
        r.setCode(MessageType.OK.getCode());
        r.setMessage(MessageType.OK.getMessage());
        r.setData(data);
        return r;
    }
}