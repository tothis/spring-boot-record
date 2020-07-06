package com.example.util;

import com.example.RedisApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = RedisApplication.class)
class RedisUtilTest {

    @Test
    void get() {
        String test = RedisUtil.get("test");
        System.out.println(test);
    }

    @Test
    void set() {
        RedisUtil.set("test", "test");
    }

    @Test
    void expire() {
        String key = "test";
        // RedisUtil.set(key, 0);
        // RedisUtil.set(key, 0, 60, TimeUnit.SECONDS);
        RedisUtil.set(key, 0, 1, TimeUnit.MINUTES);
        System.out.println(RedisUtil.increment(key));
        System.out.println(RedisUtil.increment(key, 2));
        int number = RedisUtil.get(key);
        System.out.println(number);
        System.out.println(RedisUtil.hasKey(key));
        System.out.println(RedisUtil.getExpire(key));
    }
}