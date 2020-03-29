package com.example.mapper;

import com.example.MyBatisApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MyBatisApplication.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void $if() {
        userMapper.$if("");
        userMapper.$if("1");
    }
}