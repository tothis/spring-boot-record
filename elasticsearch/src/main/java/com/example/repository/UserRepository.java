package com.example.repository;

import com.example.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 李磊
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {
}
