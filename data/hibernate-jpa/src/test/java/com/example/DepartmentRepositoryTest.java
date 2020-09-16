package com.example;

import com.example.model.Department;
import com.example.model.User;
import com.example.repository.DepartmentRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 李磊
 * @datetime 2020/2/10 16:36
 * @description 一对多映射
 */
@SpringBootTest(classes = HibernateJpaApplication.class)
class DepartmentRepositoryTest {

    private DepartmentRepository departmentRepository;

    private UserRepository userRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void before() {
        Department department1 = new Department();
        department1.setDepartmentName("部门1");
        Department department2 = new Department();
        department2.setDepartmentName("部门2");

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        User user1 = new User();
        user1.setUserName("one");
        user1.setDepartment(department1);

        User user2 = new User();
        user2.setUserName("two");
        user2.setDepartment(department2);

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void after() {
        departmentRepository.deleteAll();
    }

    @Test
    void main() {
        for (Department department : departmentRepository.findAll()) {
            System.out.println(department);
        }
    }
}