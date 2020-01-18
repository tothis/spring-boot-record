package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:19
 * @description 用户
 */
@Data
public class User {
    private Long id;
    private String userName;
    private String password;
    private Byte age;
    private String mail;
    private LocalDateTime birthday;
    private String address;
    private Boolean delFlag;
}