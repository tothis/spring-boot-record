package com.example.util;

import com.example.RedisApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = RedisApplication.class)
class RedisUtilTests {

    @Test
    void main() {
        String key = "test";
        // RedisUtil.set(key, 0);
        // RedisUtil.set(key, 0, 60, TimeUnit.SECONDS);
        // increment 的值需要 StringRedisTemplate 设置
        RedisUtil.setString(key, "0", 1, TimeUnit.MINUTES);
        System.out.println(RedisUtil.increment(key));
        System.out.println(RedisUtil.increment(key, 2));
        // StringRedisTemplate 设置的的值也需要 StringRedisTemplate 获取
        System.out.println(RedisUtil.getString(key));
        System.out.println(RedisUtil.hasKey(key));
        System.out.println(RedisUtil.getExpire(key));
    }
}
