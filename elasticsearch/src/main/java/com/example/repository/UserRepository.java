package com.example.repository;

import com.example.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 李磊
 * @datetime 2020/6/21 12:52
 * @description
 */
public interface UserRepository extends ElasticsearchRepository<User, Long> {
}