package com.example.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author 李磊
 * @datetime 2019/12/4 17:33
 * @description
 */
public class StringUtil {
    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 字符串转换成为16进制 无需Unicode编码
     *
     * @param content
     * @return
     */
    public static String stringToHex(String content) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        // 无共享变量 使用StringBuilder即可
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bs = content.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            stringBuilder.append(chars[bit]);
            bit = bs[i] & 0x0f;
            stringBuilder.append(chars[bit]);
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制直接转换成为字符串 无需Unicode解码
     *
     * @param hex
     * @return
     */
    public static String hexToString(String hex) {
        String str = "0123456789ABCDEF";
        char[] hexs = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 字符串转unicode
     *
     * @param content
     * @return
     */
    public static String stringToUnicode(String content) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            // if (ch > 255)
            stringBuilder.append("\\u" + Integer.toHexString(ch));
            // else
            // stringBuilder.append("\\" + Integer.toHexString(ch));
        }
        return stringBuilder.toString();
    }

    /**
     * unicode转字符串
     *
     * @param unicode
     * @return
     */
    public static String unicodeToString(String unicode) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            stringBuilder.append((char) index);
        }
        return stringBuilder.toString();
    }

    /**
     * 1 -> A 27 -> AA
     */
    public static String numberToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        // StringBuilder letter = new StringBuilder();
        String letter = "";
        num--;
        do {
            if (letter.length() > 0) {
                num--;
            }
            letter = ((char) (num % 26 + (int) 'A')) + letter;
            // letter.insert(0, ((char) (num % 26 + (int) 'A'))); // StringBuilder方式
            num = (num - num % 26) / 26;
        } while (num > 0);
        return letter;
    }

    /**
     * 指定产生字符串的长度length
     *
     * @param length
     * @return
     */
    public static String randomStr(int length) {
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

    // 指定随机字符串类型 0(a-z) 1(A-Z) 2(0-9)
    public static String randomStr(int length, int number) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            switch (number) {
                case 0:
                    sb.append((char) (random.nextInt(25) + 65));
                    break;
                case 1:
                    sb.append((char) (random.nextInt(25) + 97));
                    break;
                case 2:
                    sb.append(random.nextInt(10));
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(randomStr(4));
        System.out.println(randomStr(4, 0));
        System.out.println(randomStr(4, 1));
        System.out.println(randomStr(4, 2));
    }

    /**
     * A -> 1 AA -> 27
     */
    public int letterToNumber(String letter) {
        // 转换成大写
        letter = letter.toUpperCase();
        int length = letter.length();
        int num;
        int number = 0;
        for (int i = 0; i < length; i++) {
            char ch = letter.charAt(length - i - 1);
            num = ch - 'A' + 1;
            num *= Math.pow(26, i);
            number += num;
        }
        return number;
    }
}