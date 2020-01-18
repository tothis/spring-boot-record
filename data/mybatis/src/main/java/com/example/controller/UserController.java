package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020-01-16
 * @description 控制层
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation("增加用户")
    @GetMapping("add")
    public int add(User user) {
        return userService.insert(user);
    }

    /**
     * 删除
     */
    @ApiOperation("删除数据")
    @ApiResponses({@ApiResponse(code = 0, message = "删除用户", response = int.class)})
    @GetMapping("delete")
    public int delete(@ApiParam(value = "用户id", required = true) @RequestParam("id") Long id) {
        return userService.deleteById(id);
    }

    /**
     * 查询
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long")
    })
    @ApiOperation("查询单个用户")
    @GetMapping("selectById")
    public User selectById(@RequestParam("id") Long id) {
        return userService.selectById(id);
    }

    @GetMapping("findAll")
    public List<User> findAll(User user) {
        return userService.findAll(user);
    }
}
