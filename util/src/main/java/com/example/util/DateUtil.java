package com.example.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 李磊
 * @datetime 2019/09/29 09:40
 */
public class DateUtil {

    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";

    public static String dateTime(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String date(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String time(String format) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String dateTime() {
        return dateTime(FORMAT);
    }

    public static String date() {
        return date(FORMAT_DATE);
    }

    public static String time() {
        return time(FORMAT_TIME);
    }

    public static void main(String[] args) {
        System.out.println(dateTime());
        System.out.println(date());
        System.out.println(time());
    }
}