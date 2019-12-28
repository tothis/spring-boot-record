package com.example.service;

import com.example.pojo.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface UserService {

    DeleteResult deleteUser(Integer id);

    UpdateResult updateUser(Integer id, String userName);

    User selectUserById(Integer id);

    void insertUser(User user);

    List<User> listUser(String userName, Integer skip, Integer limit);
}