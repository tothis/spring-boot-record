package com.example.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 加密方式
 */
public class Encryption {

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

    private static final int REPLACENUM = 4;

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
                replace(md5, getRandomString1(replaceNum), 0)
                , getRandomString1(replaceNum), md5.length() - replaceNum);
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

    // 指定产生字符串的长度length
    public static String getRandomString1(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();

        // org.apache.commons.lang
        // RandomStringUtils的randomAlphanumeric(int length)
        // 可随机生成一个长度为length的字符串
        // return RandomStringUtils.randomAlphanumeric(10);
    }

    // 指定随机字符串类型 1(a-z) 2(A-Z) 3(0-9)
    public static String getRandomString2(int length, int number) {
        StringBuffer sb = new StringBuffer();
        // Random random = new Random();
        for (int i = 0; i < length; i++) {
            // int number= random.nextInt(3) + 1;
            long result = 0;
            switch (number) {
                case 1:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char) result));
                    break;
                case 2:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append(String.valueOf((char) result));
                    break;
                case 3:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

    // 替换指定字符串位置的字符串 var1为原字符串 var2为替换字符串 index为添加位置(从0开始)
    public static String replace(String var1, String var2, int index) {
        if (var2.length() + index > var1.length()) {
            throw new StringIndexOutOfBoundsException("替换字符串长度+索引位置>超出原字符串长度");
        }
        return var1.substring(0, index) + var2 + var1.substring(index + var2.length());
    }

    // 密钥是否相等
    public static boolean equals(String var1, String var2, int replaceNum) {
        return var1.substring(replaceNum).substring(0, var1.length() - 2 * replaceNum)
                .equals(var2.substring(replaceNum).substring(0, var1.length() - 2 * replaceNum));
    }
}