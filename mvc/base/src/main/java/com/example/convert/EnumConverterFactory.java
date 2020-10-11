package com.example.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.http.HttpStatus;

/**
 * 枚举转化类
 */
public class EnumConverterFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new EnumConverter(targetType);
    }

    private class EnumConverter<T extends Enum> implements Converter<String, T> {

        private Class<T> enumType;

        public EnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String content) {
            if (content.length() == 0) {
                return null;
            }
            return getEnum(enumType, content);
        }
    }

    public static <T extends Enum> T getEnum(Class<T> targetType, String source) {
        for (T t : targetType.getEnumConstants()) {
            if (t instanceof HttpStatus) {
                HttpStatus httpState = (HttpStatus) t;
                // 虽然此处配置转化器 但如果抛出异常 controller依然可以通过枚举字面量匹对
                if (httpState.value() == Long.valueOf(source)) {
                    return (T) httpState;
                }
            }
        }
        return null;
    }
}