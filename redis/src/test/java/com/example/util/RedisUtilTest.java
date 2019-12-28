package com.example.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
}