package com.example;

import com.example.model.User;
import com.example.model.UserDTO;
import com.example.util.BeanCopierUtil;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.InvocationTargetException;

/**
 * @author 李磊
 */
@SpringBootTest(classes = DozerApplication.class)
public class ConvertTest {
    @Test
    public void test() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        BeanCopier beanCopier = BeanCopier.create(User.class, UserDTO.class, false);
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        User user = new User() {{
            setUserName("李磊");
            setPassword("密码");
        }};
        int number = 1000_1000;

        long begin = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());
            userDTO.setPassword(user.getPassword());
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            beanCopier.copy(user, new UserDTO(), null);
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            BeanUtils.copyProperties(user, new UserDTO());
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            mapper.map(user, UserDTO.class);
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            PropertyUtils.copyProperties(new UserDTO(), user);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Test
    public void testNullToEmpty() {
        // 此写法 反射无法获取属性
        User user1 = new User() {{
            setUserName("李磊");
        }};
        User user2 = new User();
        user2.setPassword("密码");
        BeanCopierUtil.nullToEmpty(user1);
        BeanCopierUtil.nullToEmpty(user2);
        System.out.println(user1);
        System.out.println(user2);
    }
}
