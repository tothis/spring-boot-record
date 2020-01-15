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
}