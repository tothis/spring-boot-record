package com.example.util;

import java.io.UnsupportedEncodingException;
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
        return !(isBlank(cs));
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

    public static void word(String word) {

        int one = 0, two = 0, three = 0, four = 0;

        String charsetName = "utf8";

        byte[] bytes1 = new byte[0];
        try {
            bytes1 = word.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bytes1.length; ) {
            byte bytes2 = bytes1[i];
            if (bytes2 >= 0 && bytes2 <= 127) {
                byte[] bytes3 = new byte[1];
                bytes3[0] = bytes2;
                i++;
                String result = new String(bytes3);
                System.out.println("1字节字符 -> " + result);
                one++;
            }
            if ((bytes2 & 0xE0) == 0xC0) {
                byte[] bytes3 = new byte[2];
                bytes3[0] = bytes2;
                bytes3[1] = bytes1[i + 1];
                i += 2;
                String result = new String(bytes3);
                System.out.println("2字节字符 -> " + result);
                two++;
            }
            if ((bytes2 & 0xF0) == 0xE0) {
                byte[] bytes3 = new byte[3];
                bytes3[0] = bytes2;
                bytes3[1] = bytes1[i + 1];
                bytes3[2] = bytes1[i + 2];
                i += 3;
                String result = new String(bytes3);
                System.out.println("3字节字符 -> " + result);
                three++;
            }
            if ((bytes2 & 0xF8) == 0xF0) {
                byte[] bytes3 = new byte[4];
                bytes3[0] = bytes2;
                bytes3[1] = bytes1[i + 1];
                bytes3[2] = bytes1[i + 2];
                bytes3[3] = bytes1[i + 3];
                i += 4;
                String result = new String(bytes3);
                System.out.println("4字节字符 -> " + result);
                four++;
            }
        }
        System.out.printf("one   -> %-5d%ntwo   -> %-5d%nthree -> %-5d%nfour  -> %-5d%n", one, two, three, four);
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        int a = (int) (4 * Math.pow(16, 3) + 14 * Math.pow(16, 2)); // 汉字ASCII码最小值
        int b = (int) (9 * Math.pow(16, 3) + 15 * Math.pow(16, 2) + 10 * Math.pow(16, 1)) + 5; // 汉字ASCII
        for (int i = a; i <= b; i++) {
            word(String.valueOf((char) i));
        }
        word("🙃");
    }
}
