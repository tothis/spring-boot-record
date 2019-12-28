package com.example.repository;

import com.example.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 使用jpa需要实现MongoRepository接口 一旦实现就会自动继承
 * 接口提供的许多实现方法
 */
@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    /**
     * 只要符合jpa规范命名 可以不用实现该方法
     *
     * @param userName
     * @return 通过用户名模糊查询
     */
    List<User> findByUserNameLike(String userName);
}