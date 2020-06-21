package com.example.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密方式
 */
public class Encryption {

    private static final int REPLACENUM = 4;

    public static void main(String[] args) {

        // java自带工具包MessageDigest
        // System.out.println(stringToMD5("123456"));

        // spring自带工具包DigestUtils
        // System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // System.out.println(UUID.randomUUID().toString().replace("-", ""));

        // System.out.println(replace("123456", "x", 1)); // 1x3456
        // System.out.println("123456".substring(1)); // 23456
        // System.out.println("123456".substring(0, 1)); // 1

        String str = "12345678";
        System.out.println(replace(str, "sss", 5));

        System.out.println(equals("xx1234xx", "ss1234ss", 2));
    }

    /**
     * 加密用户密码并处理前后4个字符串
     */
    public static String md5(String plaintext) {
        return getciphertext(stringToMD5(plaintext), REPLACENUM);
    }

    /**
     * 比较用户密码并处理前后4个字符串
     */
    public static boolean md5Equals(String var1, String var2) {
        return equals(var1, var2, REPLACENUM);
    }

    /**
     * 对密文字符串进行局部替换
     *
     * @param md5        原字符串
     * @param replaceNum 替换成随机字符数量
     * @return
     */
    public static String getciphertext(String md5, int replaceNum) {

        if (replaceNum <= 0 || md5.length() <= replaceNum)
            return md5;

        return replace(
                replace(md5, StringUtil.randomStr(replaceNum), 0)
                , StringUtil.randomStr(replaceNum), md5.length() - replaceNum);
    }

    /**
     * 对字符串进行md5加密
     *
     * @param plaintext 明文
     * @return 加密后的密文
     */
    public static String stringToMD5(String plaintext) {
        byte[] ciphertext = null;
        try {
            ciphertext = MessageDigest.getInstance("md5").digest(plaintext.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("未找到此md5算法");
        }
        String md5code = new BigInteger(1, ciphertext).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * 替换指定字符串位置的字符串
     *
     * @param var1  原字符串
     * @param var2  替换字符串
     * @param index index为添加位置(从0开始)
     * @return
     */
    public static String replace(String var1, String var2, int index) {
        if (var2.length() + index > var1.length()) {
            throw new StringIndexOutOfBoundsException("替换字符串长度+索引位置>超出原字符串长度");
        }
        return var1.substring(0, index) + var2 + var1.substring(index + var2.length());
    }

    /**
     * 密钥是否相等
     *
     * @param var1
     * @param var2
     * @param replaceNum
     * @return
     */
    public static boolean equals(String var1, String var2, int replaceNum) {
        return var1.substring(replaceNum).substring(0, var1.length() - 2 * replaceNum)
                .equals(var2.substring(replaceNum).substring(0, var1.length() - 2 * replaceNum));
    }
}