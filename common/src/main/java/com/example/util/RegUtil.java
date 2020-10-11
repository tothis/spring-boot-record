package com.example.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * @author 李磊
 * @since 1.0
 */
@UtilityClass
public class RegUtil {
    /**
     * ^ 匹配输入字符串开始位置
     * \d 匹配一个或多个数字 '\'需被转义
     * $ 匹配输入字符串结尾位置
     */
    private final Pattern HK_PATTERN = Pattern.compile("^(5|6|8|9)\\d{7}$");
    private final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");

    /**
     * 匹配手机号
     */
    public boolean isPhoneNumber(String str) {
        return isChinaPhoneNumber(str) || isHKPhoneNumber(str);
    }

    /**
     * 大陆手机号码11位数 匹配格式 前三位固定格式+后8位任意数字
     * 前三位格式
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数字
     * 166
     * 17+3,5,6,7,8
     * 18+任意数字
     * 198,199
     */
    public boolean isChinaPhoneNumber(String str) {
        return CHINA_PATTERN.matcher(str).matches();
    }

    /**
     * 香港手机号码8位数 5|6|8|9开头+7位任意数字
     */
    public boolean isHKPhoneNumber(String str) {
        return HK_PATTERN.matcher(str).matches();
    }
}