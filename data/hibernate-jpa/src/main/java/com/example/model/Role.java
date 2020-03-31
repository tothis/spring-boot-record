package com.example.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude = "userList")
@Entity
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    private String roleName;

    /**
     * 使用@JoinTable注解添加中间表
     * name 中间表表名
     * joinCloums 当前表映射列
     * inverseJoinColumns 用户表表映射列
     * name 列名
     * referencedColumnName 被映射表主键 可省略该属性
     * <p>
     * 两个多对多直接使用@ManyToMany且不使用@JoinTable和mappedBy则直接生成两个关联表
     * 正确用法一方使用@ManyToMany+@JoinTable 另一方使用@ManyToMany(mappedBy = "xxxx")
     * 使用mappedBy方等于放弃维护机会即 使用mappedBy属于被控方 中间表数据只能由主控方删除
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_user", // 用户和角色关联表名称 不指定时默认名称为'表名1'+'_'+'表名2'
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> userList; // 角色拥有用户
}