package com.example.mapper;

import com.example.MyBatisBaseApplication;
import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest(classes = MyBatisBaseApplication.class)
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void inserts() {
        User[] users = new User[5];
        for (int i = 0; i < users.length; i++) {
            int item = i;
            users[i] = new User() {{
                setUserName("name" + item);
                setPassword("密码" + item);
            }};
        }
        mapper.inserts(users);
        // 数组或list 新增或修改都可以返回id
        Arrays.stream(users).map(User::getId).forEach(System.out::println);
    }

    @Test
    void findPage() {
        // [[{password=密码0, user_name=name0}, {password=密码1, user_name=name1}], [10]]
        System.out.println(mapper.findPage());
    }

    @Test
    void run() {
        System.out.println(mapper.run());
    }

    @Test
    void ifTest() {
        mapper.ifTest("");
        mapper.ifTest("1");
    }

    @Test
    void booleanTest() {
        System.out.println(mapper.booleanTest("1")); // true
        System.out.println(mapper.booleanTest("0")); // false
    }
}