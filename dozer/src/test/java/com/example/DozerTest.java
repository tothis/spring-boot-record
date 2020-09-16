package com.example;

import com.example.model.Product;
import com.example.model.ProductDTO;
import com.example.model.User;
import com.example.model.UserDTO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DozerApplication.class)
public class DozerTest {
    @Test
    public void test1() {
        // Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        // withMappingFiles方法加载xml配置 可配置多个xml 如 withMappingFiles("one.xml", "two.xml")
        Mapper mapper = DozerBeanMapperBuilder.create().withMappingFiles("dozer/global-config.xml").build();
        UserDTO userDTO = new UserDTO() {{
            setUserName("李磊");
            setPassword("密码");
        }};

        User result = mapper.map(userDTO, User.class);
        // 或
        // User result = new User();
        // mapper.map(userDTO, result);

        System.out.println(result);

        Product product = new Product() {{
            setName("mac");
            setFlag((byte) 0);
        }};
        System.out.println(mapper.map(product, ProductDTO.class));
        ProductDTO productDTO = new ProductDTO() {{
            setText("mac");
            setState(true);
        }};
        System.out.println(mapper.map(productDTO, Product.class));
    }

    private Mapper mapper;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Test
    public void test2() {
        UserDTO userDTO = new UserDTO() {{
            setUserName("李磊");
            setPassword("密码");
        }};
        System.out.println(mapper.map(userDTO, User.class));
    }
}