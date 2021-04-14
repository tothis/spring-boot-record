package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:19
 * @description 用户
 */
@Data
@ToString(exclude = {"roleList", "department"})
@Entity // jpa检测到@Entity时 会在数据库中生成对应表结构
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "user_name")
    private String userName;

    private String password;

    private Byte age;

    private String mail;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    // user为关系维护端 级联保存 删除address
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    // user中address_id字段参考address表中的id字段
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // 序列化时忽略department的userList属性
    @JsonIgnoreProperties("userList")
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // 添加了mappedBy属性则不能使用@JoinTable注解
    // @JsonIgnoreProperties("userList")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roleList;
}