package com.example.repository;

import com.example.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * @author 李磊
 * @datetime 2020/2/10 21:21
 * @description
 */
@Transactional
public interface LabelRepository extends JpaRepository<Label, Long> {
}