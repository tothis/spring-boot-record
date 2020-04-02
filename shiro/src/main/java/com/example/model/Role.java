package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@ApiModel("角色")
@Data
@Entity
public class Role extends BaseEntity {

    @ApiModelProperty("权限名称")
    @Column(length = 40)
    private String roleName;

    @ApiModelProperty("角色拥有权限")
    @OneToMany(mappedBy = "role")
    private List<Permission> permissionList;

    @ApiModelProperty("角色拥有用户")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> userList;
}