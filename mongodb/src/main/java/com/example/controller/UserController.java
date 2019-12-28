package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 添加用户
    @GetMapping("insert")
    public String insertUser(User user) {
        userService.insertUser(user);
        return user.getId();
    }

    // 根据id获取用户
    @GetMapping("select/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.selectUserById(id);
    }

    // 根据条件查询符合条件的用户
    @GetMapping("list")
    public List<User> listUser(
            @RequestParam(defaultValue = "null") String userName
            , @RequestParam(defaultValue = "0") Integer skip
            , @RequestParam(defaultValue = "10") Integer limit) {
        return userService.listUser(userName, skip, limit);
    }

    // 更新用户
    @GetMapping("update")
    public UpdateResult updateUser(Integer id, String userName) {
        return userService.updateUser(id, userName);
    }

    // 删除用户
    @GetMapping("delete")
    public DeleteResult deleteUser(Integer id) {
        return userService.deleteUser(id);
    }
}