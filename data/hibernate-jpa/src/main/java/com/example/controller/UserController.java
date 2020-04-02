package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:09
 * @description
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("valid1")
    /**
     * 多个实体可增加参数 如 (Object1 obj1, BindingResult res1, Object2 obj2, BindingResult res2)
     * @Valid配合BindingResult使用时 参数错误不可被@ControllerAdvice捕获 但可进入此controller中使用BindingResult获取信息
     */
    public void valid1(@Valid User user, BindingResult result) {
        System.out.println(user);
        result.getAllErrors().forEach(item ->
                System.out.println(item.getObjectName() + ' ' + item.getCodes()[1] + ' ' + item.getDefaultMessage())
        );
    }

    @GetMapping("valid2")
    public void valid2(@Valid User user) {
        System.out.println(user);
    }

    @GetMapping("valid3")
    // 参收中使用校验注解如@Range 当前类需要添加@Validated
    public void valid3(@Range(min = 1, max = 120, message = "年龄为从1-120") int age,
                       @Min(value = 1, message = "排序最小为1")
                       @Max(value = 100, message = "排序最大为100") int sort
    ) {
        System.out.println(age + ' ' + sort);
    }

    @GetMapping("page")
    public Page<User> page(String userName, @PageableDefault(sort = "id") Pageable pageable) {
        return userRepository.findUsersByUserName(userName, pageable);
    }

    List<Role> roles;
    List<User> users;

    {
        roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setRoleName("root");
        roles.add(role1);
        Role role2 = new Role();
        role1.setRoleName("staff");
        roles.add(role2);

        users = new ArrayList<>();
        User user1 = new User();
        user1.setUserName("root");
        users.add(user1);
        User user2 = new User();
        user2.setUserName("staff");
        users.add(user2);
    }

    @GetMapping("all-user")
    public List<User> allUser() {
        User user = new User();
        user.setUserName("lilei");

        roles.forEach(roleRepository::save);
        user.setRoleList(roles);

        userRepository.save(user);
        List<User> all = userRepository.findAll();
        userRepository.deleteAll();
        return all;
    }

    @GetMapping("all-role")
    public List<Role> allRole() {
        Role role = new Role();
        role.setRoleName("root");

        users.forEach(userRepository::save);
        role.setUserList(users);

        roleRepository.save(role);
        List<Role> all = roleRepository.findAll();
        roleRepository.deleteAll();
        return all;
    }
}