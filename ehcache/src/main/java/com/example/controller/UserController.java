package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/20 20:41
 * @description
 */
@RequestMapping("user")
@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("save")
    public User save(User user) {
        return userRepository.save(user);
    }

    @GetMapping("findById")
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @GetMapping("findAll")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("deleteById")
    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "删除成功";
    }
}