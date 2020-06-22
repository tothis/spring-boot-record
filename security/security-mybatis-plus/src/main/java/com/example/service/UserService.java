package com.example.service;

import com.example.model.User;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:55
 * @description
 */
public interface UserService {

    List<User> list();

    int insert(User user);
}