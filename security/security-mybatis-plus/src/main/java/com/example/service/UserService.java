package com.example.service;

import com.example.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author 李磊
 */
public interface UserService extends UserDetailsService {

    List<User> list();

    int save(User user);

    List<GrantedAuthority> selectAuthorities(String userToken);
}
