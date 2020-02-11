package com.example;

import com.example.model.Address;
import com.example.model.User;
import com.example.model.UserVO;
import com.example.repository.AddressRepository;
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
 * @description
 */
@SpringBootTest(classes = HibernateJpaApplication.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void before() {
        Address addr1 = new Address("北京");
        Address addr2 = new Address("上海");
        addressRepository.save(addr1);
        addressRepository.save(addr2);

        User user1 = new User();
        user1.setUserName("one");
        user1.setAddressId(addr1.getId());
        User user2 = new User();
        user2.setUserName("two");
        user2.setAddressId(addr2.getId());
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void main() {
        List<UserVO> UserVOs = userRepository.findAllUserVO();
        for (UserVO UserVO : UserVOs) {
            System.out.println(UserVO.getUser());
            System.out.println(UserVO.getAddress());
        }
    }
}