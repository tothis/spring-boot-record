package com.example.service;

import com.example.model.Role;
import com.example.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author 李磊
 */
public interface UserService extends UserDetailsService {

    User save(User user);

    User findByUserName(String userName);

    List<Role> findRolesById(Long userId);

    int lockUser(String userName);

    int unlockUser(String userName);
}