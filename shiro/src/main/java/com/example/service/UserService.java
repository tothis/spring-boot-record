package com.example.service;

import com.example.model.Role;
import com.example.model.User;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/20 11:42
 * @description
 */
public interface UserService {

    User save(User user);

    User findByUserName(String userName);

    List<Role> findRolesById(Long userId);

    int lockUser(String userName);

    int unlockUser(String userName);
}