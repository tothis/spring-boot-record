package com.example.runner;

import com.example.model.Permission;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.PermissionRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/03/18 22:20
 * @description
 */
@Slf4j
@Component
public class AppRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final PasswordEncoder passwordEncoder;

    public AppRunner(UserRepository userRepository, RoleRepository roleRepository
            , PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("\033[34m清除数据\033[m");

        userRepository.deleteAll();
        roleRepository.deleteAll();
        permissionRepository.deleteAll();

        System.out.println("\033[36m初始化数据\033[m");

        Permission permission = new Permission();
        permission.setPermissionName("view");
        permission.setUrl("/");
        permissionRepository.save(permission);

        Role role = new Role();
        role.setRoleName("staff");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissionList(permissions);
        roleRepository.save(role);

        User user = new User();
        user.setUserName("frank");
        user.setPassword(passwordEncoder.encode("123"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoleList(roles);
        userRepository.save(user);
    }
}