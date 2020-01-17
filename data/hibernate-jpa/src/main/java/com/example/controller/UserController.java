package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:09
 * @description
 */
@Validated
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("list")
    public Page<User> findUsersByUsername(String userName, @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return userRepository.findUsersByUserName(userName, pageable);
    }

    @GetMapping("save")
    /**
     * 多个实体可增加参数 如 (Object1 obj1, BindingResult res1, Object2 obj2, BindingResult res2)
     * @Valid必须配合BindingResult使用
     */
    public User save(@Valid User user, BindingResult result) {
        return userRepository.save(user);
    }

    @GetMapping("age")
    // 参收中使用校验注解如@Range 当前类需要添加@Validated
    public void age(@Range(min = 1, max = 120, message = "年龄为从1-120") int age,
                    @Min(value = 1, message = "排序最小为1") @Max(value = 100, message = "排序最大为100") int sort
    ) {
        System.out.println(age + " " + sort);
    }

}