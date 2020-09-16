package com.example.service.impl;

import com.example.model.Permission;
import com.example.repository.PermissionRepository;
import com.example.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/1/20 12:36
 * @description
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
}