package com.example;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest(classes = ElasticsearchApplication.class)
class ElasticsearchApplicationTests {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void contextLoads() {
        userRepository.deleteAll();
        User user = new User();
        for (int i = 0; i < 10; i++) {
            user.setName("李磊" + i);
            user.setDate(new Date());
            user.setLocalDate(LocalDate.now());
            user.setLocalDateTime(LocalDateTime.now());
            userRepository.save(user);
        }
        System.out.println("all user");
        userRepository.findAll().forEach(System.out::println);
    }
}
