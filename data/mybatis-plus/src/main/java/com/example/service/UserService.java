package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.User;

/**
 * @author 李磊
 */
public interface UserService extends IService<User> {

    Page<User> page(User entity);
}
