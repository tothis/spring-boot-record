package com.example.converter;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义字符串转换器 去掉字符串前后空格
 *
 * @author 李磊
 */
public class StringConverter implements Converter<String, String> {
    @Override
    public String convert(String source) {
        if (StrUtil.isNotEmpty(source)) {
            return source.trim();
        }
        return source;
    }
}