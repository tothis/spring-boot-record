package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李磊
 * @datetime 2020-01-16
 * @description 控制层
 */
@Api(tags = "用户操作")
@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserService> {

    @ApiOperation("分页查询")
    @PostMapping("page")
    public Page<User> page(@RequestBody User entity) {
        return super.baseService.page(entity);
    }

    /**
     * 查询
     */
    @ApiOperation("查询单个用户")
    @GetMapping("{id}")
    public User selectById(@PathVariable Long id) {
        return super.baseService.getById(id);
    }

    @ApiOperation("新增/修改")
    @PostMapping
    public boolean save(@RequestBody User entity) {
        return super.baseService.save(entity);
    }

    /**
     * 删除
     */
    @ApiOperation("删除数据")
    @DeleteMapping("{id}")
    public boolean delete(@PathVariable Long id) {
        return super.baseService.removeById(id);
    }
}