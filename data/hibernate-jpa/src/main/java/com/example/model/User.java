package com.example.model;

import com.example.validator.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:19
 * @description 用户
 */
// jpa检测到@Entity时 会在数据库中生成对应表结构
@Entity
@Table(name = "user")
@Data
public class User {
    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Column(name = "user_name")
    private String userName;

    // @Pattern(regexp = "^(?:[1-9][0-9]?|1[01][0-9]|120)$", message = "年龄在1-120")
    @Max(value = 120, message = "年龄最大为120")
    @Min(value = 1, message = "年龄最小为1")
    @Column(name = "age")
    private Byte age;

    @Length(min = 8, max = 16, message = "密码长度在[8-16]之间")
    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Date("yyyy-MM-dd")
    private String birthday;

    @Column(name = "is_del")
    @AssertFalse(message = "必须为false")
    private Boolean delFlag;
}