package com.example.repository;

import com.example.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 李磊
 * @since 1.0
 */
public interface UserRepository extends ElasticsearchRepository<User, Long> {
}