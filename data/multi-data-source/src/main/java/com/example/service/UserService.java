package com.example.service;

import com.example.model.User;

public interface UserService {
    User findById(Long id);
}