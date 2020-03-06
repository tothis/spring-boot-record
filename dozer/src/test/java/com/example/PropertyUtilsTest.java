package com.example;

import com.example.model.User;
import com.example.model.UserDTO;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author 李磊
 * @datetime 2020/3/6 21:16
 * @description
 */
public class PropertyUtilsTest {
    @Test
    public void test() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        UserDTO userDTO = new UserDTO() {{
            setUserName("李磊");
            setPassword("密码");
        }};
        User user = new User();
        PropertyUtils.copyProperties(user, userDTO);
        System.out.println(user);
    }
}