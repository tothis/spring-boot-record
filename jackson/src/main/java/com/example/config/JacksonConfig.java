package com.example.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 * Jackson 配置
 * <p>
 * Jackson 只能指定 Date 序列化格式，LocalDateTime 需要手动配置
 *
 * @author 李磊
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer builderCustomizer() {
        // 从 JSON 反序列化为 Java 对象时生效，即 get 请求的参数无法拦截（可通过 Converter 实现拦截）
        return builder -> {
            builder.serializerByType(LocalDateTime.class
                    , new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN)));
            builder.deserializerByType(LocalDateTime.class
                    , new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN)));
            // JS 数字精度小于 Java long，返回数据时把 long 类型属性转为字符串类型 Jackson
            builder.serializerByType(long.class, ToStringSerializer.instance);
            // 基本类型和基本类型包子类型需分开设置
            builder.serializerByType(Long.class, ToStringSerializer.instance);
        };
    }
}
