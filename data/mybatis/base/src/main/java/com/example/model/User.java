package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李磊
 */
@Data
public class User extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Byte age;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 地址
     */
    private List<Address> addresses;

    /**
     * 类型
     */
    private Type type;

    // 被 MyBatis 映射结果集需声明为 static

    /**
     * 地址
     */
    @Data
    public static class Address {
        private Long id;
        private Long userId;
        private String detail;
    }

    /**
     * 类型
     */
    @Data
    static class Type {
        private Long id;
        private String detail;
    }
}
