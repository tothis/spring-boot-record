package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 */
@RequestMapping("user")
@RestController
public class UserController {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public UserController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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
