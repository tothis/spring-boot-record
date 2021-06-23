package com.example.entity;

import com.example.type.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 统一返回数据
 *
 * @author 李磊
 */
@NoArgsConstructor
@Getter
@Setter
public class ResultEntity<T> {

    private static final ResultEntity OK;

    static {
        ResultEntity r = new ResultEntity<>();
        r.setCode(MessageType.OK.getCode());
        r.setMessage(MessageType.OK.getMessage());
        OK = r;
    }

    private int code;

    private String message;

    private T data;

    public ResultEntity(MessageType type) {
        this.code = type.getCode();
        this.message = type.getMessage();
    }

    public static <T> ResultEntity<T> ok(T data) {
        if (data == null) {
            return OK;
        }
        ResultEntity<T> r = new ResultEntity<>();
        r.setCode(MessageType.OK.getCode());
        r.setMessage(MessageType.OK.getMessage());
        r.setData(data);
        return r;
    }
}
