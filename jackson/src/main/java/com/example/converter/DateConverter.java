package com.example.converter;

import com.example.util.StringUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        Date date = null;
        if (!StringUtil.isBlank(source)) {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
            LocalDateTime parse = LocalDateTime.parse(source, formatter);
            date = Date.from(parse.toInstant(ZoneOffset.UTC));
        }
        return date;
    }
}