package com.example.model;

import lombok.Data;

/**
 * @author 李磊
 * @datetime 2020/2/10 16:30
 * @description
 */
@Data
public class UserVO {
    private User user;
    private Address address;

    public UserVO(User user, Address address) {
        this.user = user;
        this.address = address;
    }
}