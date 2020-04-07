package com.example;

import com.example.model.Permission;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.PermissionRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/2/10 16:36
 * @description 多对多映射
 */
@SpringBootTest(classes = HibernateJpaApplication.class)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @BeforeEach
    void before() {

        User user1 = new User();
        user1.setUserName("one");
        userRepository.save(user1);
        User user2 = new User();
        user2.setUserName("two");
        userRepository.save(user2);

        Permission permission1 = new Permission();
        permission1.setPermissionName("view");
        permissionRepository.save(permission1);
        Permission permission2 = new Permission();
        permission2.setPermissionName("edit");
        permissionRepository.save(permission2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        List<Permission> permissionList = new ArrayList<>();
        permissionList.add(permission1);
        permissionList.add(permission2);

        Role role1 = new Role();
        role1.setRoleName("角色1");
        role1.setUserList(userList);
        role1.setPermissionList(permissionList);

        Role role2 = new Role();
        role2.setRoleName("角色2");
        role2.setUserList(userList);
        role2.setPermissionList(permissionList);

        roleRepository.save(role1);
        roleRepository.save(role2);
    }

    @AfterEach
    void after() {
        roleRepository.deleteAll();
    }

    @Test
    void main() {
        for (Role role : roleRepository.findAll()) {
            System.out.println(role);
        }
    }
}