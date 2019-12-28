package com.example.repository.impl;

import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * 自定义不需要实现接口 只需要命名 接口+Impl即可
 * spring会自动根据名字找到这个类加入容器
 */
@Repository
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User findUserByIdOrUserName(Integer id, String userName) {
        // 自定义查询逻辑
        return null;
    }
}