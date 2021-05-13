package com.example.repository;

import com.example.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李磊
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}