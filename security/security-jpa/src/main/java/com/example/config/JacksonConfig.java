package com.example.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 李磊
 * @datetime 2020/4/9 21:59
 * @description jackson只能指定Date序列化格式 LocalDateTime需要手动配置
 */
@Configuration
public class JacksonConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer builderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class
                    , new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern)));
            builder.deserializerByType(LocalDateTime.class
                    , new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern)));

        };
    }
}