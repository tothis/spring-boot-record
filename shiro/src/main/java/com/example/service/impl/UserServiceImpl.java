package com.example.service.impl;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/20 11:44
 * @description
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<Role> findRolesById(Long userId) {
        return userRepository.findRolesById(userId);
    }

    @Override
    public int lockUser(String userName) {
        return userRepository.lockUser(userName);
    }

    @Override
    public int unlockUser(String userName) {
        return userRepository.unlockUser(userName);
    }
}