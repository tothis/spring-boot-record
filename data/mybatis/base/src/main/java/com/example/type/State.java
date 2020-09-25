package com.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 李磊
 * @datetime 2020/3/4 9:53
 * @description
 */
@Getter
@ToString
@AllArgsConstructor
public enum State {
    NORMAL(0, "正常"), DELETE(1, "删除");
    private final int code;
    private final String text;

    public static State type(int code) {
        for (State state : State.values()) {
            if (state.getCode() == code) return state;
        }
        throw new RuntimeException("枚举解析失败");
    }

    public static State type(String text) {
        for (State state : State.values()) {
            if (state.getText().equals(text)) return state;
        }
        throw new RuntimeException("枚举解析失败");
    }
}