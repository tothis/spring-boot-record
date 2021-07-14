package com.example.util;

import com.example.RedisTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RedisTestApplication.class)
class RedisUtilTests {

    @Test
    void main() {
        String key = "test";
        // RedisUtil.set(key, 0);
        // RedisUtil.set(key, 0, 60, TimeUnit.SECONDS);
        // increment 的值需要 StringRedisTemplate 设置
        RedisUtil.setString(key, "0", 1, TimeUnit.MINUTES);
        assertThat(RedisUtil.increment(key)).isEqualTo(1);
        assertThat(RedisUtil.increment(key, 2)).isEqualTo(3);
        // StringRedisTemplate 设置的的值也需要 StringRedisTemplate 获取
        assertThat(RedisUtil.getString(key)).isEqualTo("3");
        assertThat(RedisUtil.hasKey(key)).isTrue();
        assertThat(RedisUtil.getExpire(key)).isEqualTo(60);
    }
}
