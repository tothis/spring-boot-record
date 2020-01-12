package com.example.util;

import java.util.regex.Pattern;

/**
 * 正则表达式验证工具类
 *
 * @author 李磊
 */
public class RegexVerifyUtil {

    /**
     * . 匹配除换行符以外的任意字符
     * \w 匹配字母或数字或下划线或汉字 等价于'[A-Za-z0-9_]'
     * \s 匹配任意的空白符
     * \d 匹配数字
     * \b 匹配单词的开始或结束
     * ^ 匹配字符串的开始
     * $ 匹配字符串的结束
     * + 表示重复一次或者多次
     * * 表示重复零次或者多次
     * {n,m} 表示n 到 m 次
     * \w能不能匹配汉字要视你的操作系统和你的应用环境而定
     */
    /**
     * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
     * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
     * ^[\u4E00-\u9FA5]+$ 匹配简体
     */
    public static final String REGEX_CHINESE_CHAR = "^[\u4E00-\u9FFF]+$";

    /**
     * 整数
     */
    public static final String REGEX_INTEGER = "^[0-9]*$";

    /**
     * 数字
     */
    public static final String REGEX_NUMBER = "^-?\\d+(\\.\\d+)?$";

    /**
     * 正则表达式 验证用户名
     * 说明 5-17位字母数字下划线
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式 验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式 验证手机号
     */
    public static final String REGEX_MOBILE = "^(1[3-8])\\d{9}$";

    /**
     * 正则表达式 验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";

    /**
     * 正则表达式 验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{17}[a-z0-9A-Z]{1}$)|(^\\d{14}[a-z0-9A-Z]{1}$)";

    /**
     * 正则表达式 验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式 验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式 验证银行卡
     */
    public static final String REGEX_BANK_CARD = "^(\\d{15,30})$";

    // Matcher m = Pattern.compile("\\d\\d\\d").matcher("a123b");
    // System.out.println(m.find()); // true
    // System.out.println(m.matches()); // false

    /**
     * @param str   源字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean match(String regex, String str) {
        return Pattern.matches(regex, str);
    }

    /**
     * @param str   源字符串
     * @param regex 正则表达式
     * @return 是否存在
     */
    public static boolean find(String regex, String str) {
        return Pattern.compile(regex).matcher(str).find();
    }

    /**
     * @param str 源字符串
     * @return 是否为数字
     */
    public static boolean isNumber(String str) {
        return match(REGEX_NUMBER, str);
    }

    /**
     * 判断是否是密码
     */
    public static boolean isPassWord(String value) {

        return match(REGEX_PASSWORD, value);
    }

    /**
     * 判断是否是手机号码
     */
    public static boolean isPhone(String value) {

        return match(REGEX_MOBILE, value);
    }

    /**
     * 判断是否是邮箱
     */
    public static boolean isEmail(String value) {

        return match(REGEX_EMAIL, value);
    }

    /**
     * 判断是否是身份证
     */
    public static boolean isIDCard(String value) {

        return match(REGEX_ID_CARD, value);
    }

    /**
     * 判断是否是URL
     */
    public static boolean isURL(String value) {

        return match(REGEX_URL, value);
    }

    /**
     * 判断是否是IP
     */
    public static boolean isIP(String value) {

        return match(REGEX_IP_ADDR, value);
    }


    /**
     * 判断是否是整数
     */
    public static boolean isInteger(String value) {

        return match(REGEX_INTEGER, value);
    }

    /**
     * 判断是否是小数
     */
    public static boolean isReal(String value) {

        return match("^[0-9]+\\.{0,1}[0-9]{0,10}$", value);
    }

    /**
     * 判断是否是用户名
     */
    public static boolean isBankCard(String value) {

        return match(REGEX_BANK_CARD, value);
    }

    /**
     * 判断字符是否是汉字
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(char str) {
        return RegexVerifyUtil.match(REGEX_CHINESE_CHAR, String.valueOf(str));
    }

    /**
     * 判断字符串是否是汉字
     *
     * @param str
     * @return
     */
    public static boolean isChineseWord(String str) {
        return RegexVerifyUtil.match("^[\u4E00-\u9FFF]+$", str);
    }
}