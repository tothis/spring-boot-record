package com.example.controller;

import com.example.model.User;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李磊
 * @datetime 2020-01-16
 * @description 控制层
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private IUserService iUserService;

    /**
     * 新增
     */
    // @ApiOperation("新增数据")
    @PostMapping("add")
    public int add(@RequestBody User entity) {
        return iUserService.insert(entity);
    }

    /**
     * 删除
     */
    // @ApiOperation("删除数据")
    @GetMapping("delete")
    public int delete(@RequestParam("id") Long id) {
        return iUserService.deleteById(id);
    }

    /**
     * 查询
     */
    // @ApiOperation("根据id查询")
    @GetMapping("selectById")
    public User selectById(@RequestParam("id") Long id) {
        return iUserService.selectById(id);
    }
}
