package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.model.LoginUser;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:55
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> list() {
        return userMapper.selectList(null);
    }

    @Override
    public int save(User user) {
        if (user.getId() == null) {
            return userMapper.insert(user);
        } else {
            return userMapper.updateById(user);
        }
    }

    @Override
    public LoginUser loadUserByUsername(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public List<GrantedAuthority> selectAuthorities(String userToken) {
        List<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("user:view,ROLE_user");
        authorities.add(new SimpleGrantedAuthority("ROLE_root"));
        authorities.add(new SimpleGrantedAuthority("user:edit"));
        return authorities;
    }
}