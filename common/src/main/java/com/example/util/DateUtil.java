package com.example.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 李磊
 * @datetime 2019/09/29 09:40
 */
public class DateUtil {

    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";

    public static String currentDateTime(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String currentDate(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String currentTime(String format) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String currentDateTime() {
        return currentDateTime(FORMAT);
    }

    public static String currentDate() {
        return currentDate(FORMAT_DATE);
    }

    public static String currentTime() {
        return currentTime(FORMAT_TIME);
    }

    public static LocalDateTime dateTime(String value) {
        return dateTime(value, FORMAT);
    }

    public static LocalDateTime dateTime(String value, String format) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(format));
    }

    public static void main(String[] args) {
        System.out.println(currentDateTime());
        System.out.println(currentDate());
        System.out.println(currentTime());

        Calendar c = Calendar.getInstance();
        // 获取三天后的日期
        c.add(Calendar.DATE, 3);
        System.out.println(new Date());
        System.out.println(c.getTime());
    }
}