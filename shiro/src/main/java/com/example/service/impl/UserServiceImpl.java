package com.example.service.impl;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李磊
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
