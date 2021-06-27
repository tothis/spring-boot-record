package com.example.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
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

        };
    }

    // 配置 application.yml 中或配置此 bean
    // @Bean
    // spring 中无 ObjectMapper 实例时调用
    // @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        // 属性为空字符串或 null 时不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 未知属性反序列化不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.setDateFormat(new SimpleDateFormat(NORM_DATETIME_PATTERN));
        return objectMapper;
    }
}
