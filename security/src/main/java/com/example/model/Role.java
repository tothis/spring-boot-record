package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@ApiModel("角色")
@Data
@ToString(exclude = {"userList", "permissionList"})
@NamedEntityGraph(attributeNodes = @NamedAttributeNode("userList"))
@Entity
public class Role extends BaseEntity {

    @ApiModelProperty("权限名称")
    @Column(length = 40)
    private String roleName;

    @ApiModelProperty("角色拥有权限")
    @JsonIgnoreProperties("roleList") // 避免递归死循环
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")}
    )
    private List<Permission> permissionList;

    @ApiModelProperty("角色拥有用户")
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> userList;
}