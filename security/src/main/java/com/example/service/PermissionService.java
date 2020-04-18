package com.example.service;

import com.example.model.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();
}