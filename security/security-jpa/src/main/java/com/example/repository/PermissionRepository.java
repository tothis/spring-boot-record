package com.example.repository;

import com.example.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李磊
 * @datetime 2020/1/20 12:39
 * @description
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}