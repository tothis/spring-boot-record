package com.example.repository;

import com.example.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李磊
 */
public interface LabelRepository extends JpaRepository<Label, Long> {
}
