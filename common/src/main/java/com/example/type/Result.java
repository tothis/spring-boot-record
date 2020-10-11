package com.example.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lilei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Result<T> {

    private long code;

    private String message;

    private T data;

    public Result(HttpState state) {
        this.code = state.code();
        this.message = state.message();
    }

    public Result(HttpState state, T data) {
        this.code = state.code();
        this.message = state.message();
        this.data = data;
    }

    // 成功
    public static Result success() {
        return new Result(HttpState.OK);
    }

    public static Result success(Object data) {
        return new Result(HttpState.OK, data);
    }

    // 失败
    public static Result fail() {
        return new Result(HttpState.BAD_REQUEST);
    }

    public static Result fail(Object data) {
        return new Result(HttpState.BAD_REQUEST, data);
    }
}