package com.example.controller;

import com.example.pojo.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("jpa")
public class UserJPAController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("{id}")
    public User findUserById(@PathVariable("id") Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User("-1","查询不到该用户"));
    }
}