package com.example.model;

import lombok.Data;

/**
 * @author 李磊
 * @datatime 2020-01-16
 * @description 实体
 */
@Data
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 年龄
     */
    private Boolean age;

    /**
     * 地址
     */
    private String address;

    /**
     * 密码
     */
    private String password;

}