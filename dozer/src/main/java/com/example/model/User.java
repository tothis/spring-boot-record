package com.example.model;

import lombok.Data;

/**
 * @author 李磊
 */
@Data
public class User {
    private String userName;
    private String password;
    private Address address;
    private Remark remark;
    private Role role;

    @Data
    public static class Address {
        private String content;
    }

    @Data
    public class Remark {
        private String content;
    }
}