package com.example.converter;

import com.example.util.StringUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        LocalDateTime date = null;
        if (!StringUtil.isBlank(source)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
            date = LocalDateTime.parse(source, formatter);
        }
        return date;
    }
}