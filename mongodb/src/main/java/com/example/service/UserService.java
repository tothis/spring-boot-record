package com.example.service;

import com.example.pojo.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface UserService {

    DeleteResult deleteUser(Long id);

    UpdateResult updateUser(Long id, String userName);

    User selectUserById(Long id);

    void insertUser(User user);

    List<User> listUser(String userName, Long skip, Long limit);
}