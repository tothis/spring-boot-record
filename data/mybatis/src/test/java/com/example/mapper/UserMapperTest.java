package com.example.mapper;

import com.example.MyBatisApplication;
import com.example.model.User;
import com.example.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest(classes = MyBatisApplication.class)
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void $if() {
        mapper.$if("");
        mapper.$if("1");
    }

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
    void split() {
        Arrays.stream(mapper.split("a,b,c,")).forEach(System.out::println);
    }

    @Test
    void run() {
        System.out.println(StringUtil.uuid());
        System.out.println(mapper.run());
    }

    @Test
    void user() {
        System.out.println(mapper.user1());
        System.out.println(mapper.user2());
    }
}