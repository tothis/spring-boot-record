package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:08
 * @description
 */
@Slf4j
@RequestMapping("user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("hasRole('root') or hasAuthority('user:view')")
    @ApiOperation("查询用户列表")
    @GetMapping("list")
    public List<User> list() {
        return userService.list();
    }

    @PreAuthorize("hasAuthority('ROLE_root') or hasAuthority('user:view')")
    @ApiOperation("查询用户权限")
    @GetMapping("authorities1")
    public List<String> authorities1() {
        return super.authorities();
    }

    @GetMapping("authorities2")
    public List<GrantedAuthority> authorities2(@RequestHeader String authorization) {
        return JwtUtil.authorities(authorization);
    }

    /**
     * 注册用户 默认开启白名单
     *
     * @param user
     */
    @ApiOperation("保存用户")
    @PostMapping("save")
    public int save(@RequestBody User user) {
        UserDetails userDetails = userService.loadUserByUsername(user.getUserName());
        if (userDetails != null) {
            throw new RuntimeException("用户已经存在");
        }
        // user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.save(user);
    }
}