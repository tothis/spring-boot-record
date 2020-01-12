package com.example.util;

/**
 * @author 李磊
 * @datetime 2019/12/4 17:33
 * @description
 */
public class StringUtil {
    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    public static <T extends CharSequence> boolean isBlank(T str) {
        return str == null || str.length() == 0;
    }
}