package com.example.repository;

import com.example.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {

    /**
     * 只要符合jpa规范命名 可以不用实现该方法
     *
     * @param userName
     * @return 通过用户名模糊查询
     */
    List<User> findByUserNameLike(String userName);

    User findUserTest(Long id);
}