package com.example.config;

import com.example.converter.DateConverter;
import com.example.converter.LocalDateTimeConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 李磊
 * @datetime 2020/3/16 22:07
 * @description
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // controller方法get请求时会使用converter转化
        registry.addConverter(new DateConverter());
        registry.addConverter(new LocalDateTimeConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(ObjectMapper mapper) {
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @SneakyThrows
            @Override
            public void serialize(Object o, JsonGenerator g, SerializerProvider s) {
                String fieldName = g.getOutputContext().getCurrentName();
                // 反射获取字段类型
                Field field = g.getCurrentValue().getClass().getDeclaredField(fieldName);
                if (Objects.equals(field.getType(), String.class)) {
                    // 字符串型空值""
                    g.writeString("");
                    return;
                } else if (Objects.equals(field.getType(), List.class)) {
                    // 列表型空值返回[]
                    g.writeStartArray();
                    g.writeEndArray();
                    return;
                } else if (Objects.equals(field.getType(), Map.class)) {
                    // map型空值返回{}
                    g.writeStartObject();
                    g.writeEndObject();
                    return;
                }
                // 默认返回""
                g.writeString("");
            }
        });
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        return converter;
    }
}