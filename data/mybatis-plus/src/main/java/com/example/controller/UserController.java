package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李磊
 * @datetime 2020-01-16
 * @description 控制层
 */
@Api(tags = "用户操作")
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation("增加用户")
    @PostMapping("add")
    public int add(@RequestBody User entity) {
        return userService.insert(entity);
    }

    /**
     * 删除
     */
    @ApiOperation("删除数据")
    @GetMapping("delete")
    public int delete(Long id) {
        return userService.deleteById(id);
    }

    /**
     * 查询
     */
    @ApiOperation("查询单个用户")
    @GetMapping("selectById")
    public User selectById(Long id) {
        return userService.selectById(id);
    }
}
