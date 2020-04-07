package com.example.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@ToString(exclude = "roleList")
@Entity
public class Permission extends BaseEntity {

    private String permissionName;

    // 添加了mappedBy属性则不能使用@JoinTable注解
    @ManyToMany(mappedBy = "permissionList")
    private List<Role> roleList;
}