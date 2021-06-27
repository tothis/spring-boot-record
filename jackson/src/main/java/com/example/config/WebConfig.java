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

import java.util.List;

/**
 * @author 李磊
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Controller方法不接受JSON参数时会使用converter转化
        registry.addConverter(new DateConverter());
        registry.addConverter(new LocalDateTimeConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(ObjectMapper mapper) {
        // 返回JSON null值处理
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @SneakyThrows
            @Override
            public void serialize(Object o, JsonGenerator g, SerializerProvider s) {
                String fieldName = g.getOutputContext().getCurrentName();
                // 反射获取字段类型
                Class<?> type = g.getCurrentValue().getClass().getDeclaredField(fieldName).getType();
                if (String.class.equals(type)) {
                    // 字符串型空值""
                    g.writeString("");
                } else if (List.class.equals(type) || type.isArray()) {
                    // 列表型空值返回[]
                    g.writeStartArray();
                    g.writeEndArray();
                } else {
                    // 默认返回
                    g.writeStartObject();
                    g.writeEndObject();
                }
            }
        });
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        return converter;
    }
}
