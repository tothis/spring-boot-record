package com.example.controller;

import com.example.model.Inner;
import com.example.model.Tree;
import com.example.model.User;
import com.example.service.UserService;
import com.example.type.State;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 李磊
 * @datetime 2020-01-16
 * @description 控制层
 */
@RestController
@Api(tags = "user接口")
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("增加用户")
    @PostMapping("add")
    public int add(User user) {
        return userService.insert(user);
    }

    /**
     * 删除
     */
    @ApiOperation("删除数据")
    @DeleteMapping("{id}")
    public int delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    /**
     * 查询
     */
    @ApiOperation("查询单个用户")
    @GetMapping("user/{id}")
    public User selectById(@PathVariable Long id) {
        return userService.selectById(id).orElseThrow(() ->
                new IllegalArgumentException("用户不存在"));
    }

    @ApiOperation("查询所有用户")
    @GetMapping("user")
    public List<User> findAll(User user) {
        return userService.findAll(user);
    }

    @ApiOperation("查询结果封装为map")
    @GetMapping("find-map")
    public Map<Long, User> findMap(@RequestParam(required = false) Map params) {
        return userService.findMap(params);
    }

    @GetMapping("array")
    public String arrayTest(String[] values) {
        return userService.arrayTest(values);
    }

    @GetMapping("tree")
    public List<Tree> tree(Long parentId) {
        return userService.tree(parentId);
    }

    @GetMapping("insert-enum")
    // controller转化枚举参考 https://gitee.com/tothis/spring-boot-record/blob/master/mvc/base/src/main/java/com/example/controller/BaseController.java
    public int insertEnum() {
        return userService.insertEnum(State.DELETE);
    }

    @GetMapping("find-enum")
    public State findEnum() {
        return userService.findEnum();
    }

    @PostMapping("inner")
    public String inner(@RequestBody Inner inner) {
        return userService.inner(inner);
    }
}