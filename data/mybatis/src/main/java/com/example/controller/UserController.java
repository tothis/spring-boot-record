package com.example.controller;

import com.example.model.Tree;
import com.example.model.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserService userService;

    @ApiOperation("增加用户")
    @GetMapping("add")
    public int add(HttpServletRequest request, User user) {
        String token = request.getParameter("token");
        return userService.insert(user);
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
    @GetMapping("select-by-id")
    public User selectById(Long id) {
        return userService.selectById(id);
    }

    @ApiOperation("查询所有用户")
    @GetMapping("find-all")
    public List<User> findAll(User user) {
        return userService.findAll(user);
    }

    @ApiOperation("查询结果封装为map")
    @GetMapping("find-map")
    // 使用@RequestParam把参数封装为Map required为true参数也可不传
    public Map<Long, User> findMap(@RequestParam Map params) {
        return userService.findMap(params);
    }

    @GetMapping("boolean")
    public boolean booleanTest(String value) {
        return userService.booleanTest(value);
    }

    @GetMapping("array")
    public String arrayTest(String[] values) {
        return userService.arrayTest(values);
    }

    @GetMapping("tree1")
    public List<Tree> tree1(Long parentId) {
        return userService.findAllTreeByParentId1(parentId);
    }

    @GetMapping("tree2")
    public List<Tree> tree2(Long parentId) {
        return userService.findAllTreeByParentId2(parentId);
    }
}