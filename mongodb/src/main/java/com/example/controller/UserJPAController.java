package com.example.controller;

import com.example.pojo.User;
import com.example.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author 李磊
 * @since 1.0
 */
@RestController
@RequestMapping("jpa")
public class UserJPAController {

    private final UserRepository userRepository;

    public UserJPAController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{id}")
    public User findUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User() {{
            setId(-1L);
            setUserName("查询不到该用户");
        }});
    }

    @GetMapping("userName/{userName}")
    public List<User> findByUserNameLike(@PathVariable String userName) {
        return userRepository.findByUserNameLike(userName);
    }

    @GetMapping("test/{id}")
    public User findByUserNameLike(@PathVariable Long id) {
        return userRepository.findUserTest(id);
    }
}