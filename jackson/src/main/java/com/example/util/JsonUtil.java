package com.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2019/8/15 23:15:35
 * @description jackson json工具类
 */
public final class JsonUtil {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 序列化时候统一日期格式
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 序列化时不序列化值为null或空字符串的属性
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 是否允许单引号包含key和value值 默认false
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 是否允许使用非双引号包含key值 默认false
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public static <T> T jsonToObject(String content, Class<T> valueType) {
        try {
            return MAPPER.readValue(content, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> String objectToJson(T value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        List<User> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int j = i;
            list1.add(new User() {{
                setId(Long.valueOf(j));
                setUserName(String.valueOf(j));
            }});
        }

        String json = null;
        try {
            json = mapper.writeValueAsString(list1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<User> list2 = new ArrayList<>();
        try {
            // 使用TypeReference反序列化
            TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
            };
            // 使用JavaType反序列化
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, User.class);

            list2 = mapper.readValue(json, typeReference);
            list2 = mapper.readValue(json, javaType);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        list2.forEach(System.out::println);
    }

    @Data
    static class User {
        private Long id;
        private String userName;
    }
}