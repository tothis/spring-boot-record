package com.example.controller;

import com.example.model.User;
import com.example.repository.EhcacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/20 20:58
 * @description
 */
@RequestMapping("cache")
@RestController
public class EhcacheController {

    @Autowired
    private EhcacheRepository ehcacheRepository;

    @GetMapping("save")
    public User save(User user) {
        return ehcacheRepository.save(user);
    }

    @GetMapping("findById")
    public User findById(Long id) {
        return ehcacheRepository.findById(id).get();
    }

    @GetMapping("findAll")
    public List<User> findAll() {
        return ehcacheRepository.findAll();
    }

    @GetMapping("deleteById")
    public String deleteById(Long id) {
        ehcacheRepository.deleteById(id);
        return "删除成功";
    }
}