package com.example;

import com.example.model.User;
import com.example.model.UserDTO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;

/**
 * @author 李磊
 * @datetime 2020/3/6 22:12
 * @description
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
        long number = 1000_1000;

        long begin = System.currentTimeMillis();
        for (long i = 0L; i < number; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());
            userDTO.setPassword(user.getPassword());
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (long i = 0L; i < number; i++) {
            beanCopier.copy(user, new UserDTO(), null);
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (long i = 0L; i < number; i++) {
            BeanUtils.copyProperties(user, new UserDTO());
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (long i = 0L; i < number; i++) {
            mapper.map(user, UserDTO.class);
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (long i = 0L; i < number; i++) {
            PropertyUtils.copyProperties(new UserDTO(), user);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }
}