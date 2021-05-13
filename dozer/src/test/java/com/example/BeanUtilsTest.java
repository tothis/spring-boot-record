package com.example;

import com.example.model.User;
import com.example.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 李磊
 */
@SpringBootTest(classes = DozerApplication.class)
public class BeanUtilsTest {
    @Test
    public void test() {
        UserDTO userDTO = new UserDTO() {{
            setUserName("李磊");
            setPassword("密码");
        }};
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        System.out.println(user);
    }
}