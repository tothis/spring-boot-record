package com.example.repository;

import com.example.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李磊
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
