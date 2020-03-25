package com.example;

import com.example.model.Product;
import com.example.model.ProductDTO;
import com.example.model.User;
import com.example.model.UserDTO;
import com.example.util.BeanCopierUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanCopier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 李磊
 * @datetime 2020/3/6 10:00
 * @description
 */
@SpringBootTest(classes = DozerApplication.class)
public class BeanCopierTest {
    @Test
    public void test1() {
        // BeanCopier只拷贝名称和类型都相同的属性
        BeanCopier beanCopier = BeanCopier.create(UserDTO.class, User.class, false);
        UserDTO userDTO = new UserDTO() {{
            setUserName("李磊");
            setPassword("密码");
        }};
        User user = new User();
        beanCopier.copy(userDTO, user, null);
        userDTO.setUserName("frank");
        System.out.println(user);
    }

    @Test
    public void test2() {
        BeanCopier beanCopier = BeanCopier.create(ProductDTO.class, Product.class, true);
        ProductDTO productDTO = new ProductDTO() {{
            setText("mac");
            setState(true);
            setDateTime1(LocalDateTime.now());
            setDateTime2(LocalDateTime.now());
        }};
        Product product = new Product();
        // 使用Converter 则BeanCopier只拷贝Converter定义规则的属性
        // 且处理几次值 会调用几次convert方法
        beanCopier.copy(productDTO, product, (Object value, Class target, Object context) -> {
            System.out.print("value -> ");
            System.out.println(value);
            System.out.print("context -> ");
            System.out.println(context);
            System.out.print("target -> ");
            System.out.println(target);
            if (value instanceof LocalDateTime && target.equals(String.class)) {
                LocalDateTime dateTime = (LocalDateTime) value;
                return dateTime.format(DateTimeFormatter.ofPattern("yyyy"));
            }
            return null;
        });
        System.out.println(product);
    }

    @Test
    public void test3() {
        User user = new User();
        user.setUserName("frank");
        user.setPassword("密码");
        UserDTO userDTO = new UserDTO();
        BeanCopierUtil.copy(user, userDTO);
        System.out.println(userDTO);
        System.out.println(BeanCopierUtil.convert(user, UserDTO.class));
    }
}