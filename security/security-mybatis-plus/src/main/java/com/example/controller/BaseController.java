package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description
 */
@Slf4j
public abstract class BaseController<M> {

    @Autowired
    protected BCryptPasswordEncoder encoder;

    @Autowired
    protected M baseService;

    /**
     * 获取用户所拥有权限列表
     */
    public List<String> authorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}