package com.example;

import com.example.model.*;
import com.example.util.BeanCopierUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanCopier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
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
            setAddress(new Address() {{
                setContent("地址");
            }});
            setRemark(new UserDTO().new Remark() {{
                setContent("备注");
            }});
            setRole(new Role() {{
                setContent("root");
            }});
        }};
        User user = new User();
        beanCopier.copy(userDTO, user, null);
        userDTO.setUserName("frank");
        // 复制类时 属性类型为内部类属性不可被复制
        System.out.println(user);
        UserDTO.Remark remark1 = new UserDTO().new Remark();
        Remark remark2 = new Remark();
        Address address1 = new Address();
        UserDTO.Address address2 = new UserDTO.Address();
        Role role = new Role();
        BeanCopierUtil.copy(userDTO.getRemark(), remark1);
        BeanCopierUtil.copy(userDTO.getRemark(), remark2);
        BeanCopierUtil.copy(userDTO.getAddress(), address1);
        BeanCopierUtil.copy(userDTO.getAddress(), address2);
        BeanCopierUtil.copy(userDTO.getRole(), role);
        System.out.println(remark1); // 生效
        System.out.println(remark2); // 生效
        System.out.println(address1); // 生效
        System.out.println(address2); // 生效
        System.out.println(role); // 生效
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

    // BeanCopierUtil测试
    @Test
    public void test3() {
        User user = new User();
        user.setUserName("frank");
        user.setPassword("密码");
        UserDTO userDTO = new UserDTO();

        System.out.println(BeanCopierUtil.convert(user, UserDTO.class));

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000_1000; i++) {
            BeanCopierUtil.copy(user, userDTO);
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (int i = 0; i < 1000_1000; i++) {
            BeanCopierUtil.convert(user, UserDTO.class);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

    // BeanCopierUtil测试List
    @Test
    public void test4() {
        User user1 = new User();
        user1.setUserName("frank");
        user1.setPassword("123456");
        User user2 = new User();
        user2.setUserName("james");
        user2.setPassword("password");
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        List<UserDTO> result = BeanCopierUtil.convertList(list, UserDTO.class);
        System.out.println(result);
    }
}