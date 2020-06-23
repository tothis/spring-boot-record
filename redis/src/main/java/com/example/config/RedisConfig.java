package com.example.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 李磊
 * @datetime 2019/12/24 23:43
 * @description
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        // 开启事务
        // redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置value的序列化规则和 key的序列化规则
        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认使用jdk序列化)
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class) {{
            setObjectMapper(new ObjectMapper() {{
                setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                // enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                // enableDefaultTyping已被标为作废 使用activateDefaultTyping方法代替
                activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
            }});
        }});
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}