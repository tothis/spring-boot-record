package com.example;

import com.example.model.Address;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/2/10 16:36
 * @description 一对一映射
 */
@SpringBootTest(classes = HibernateJpaApplication.class)
class UserRepositoryTest {

    /**
     * 无论单向还是双向关联 保存都由关系维护端进行操作
     * 一对一映射 其实为关系维护端的映射 关系维护端只关联一个被维护端 被维护端关联数据不受影响
     */
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void before() {
        /**
         * 建议先保存不维护关联关系一方 即无外键一方 不会多出update语句
         */
        Address addr1 = new Address("北京");
        Address addr2 = new Address("上海");

        User user1 = new User();
        user1.setUserName("one");
        user1.setAddress(addr1);
        User user2 = new User();
        user2.setUserName("two");
        user2.setAddress(addr2);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }

    @Test
    void main() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}