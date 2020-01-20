package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ApiModel(value = "com.example.model.Role", description = "权限")
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据主键", dataType = "Long")
    @Id // 标识主键
    @Column(name = "id", columnDefinition = "bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键'")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    @ApiModelProperty(value = "权限名称", dataType = "String")
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "role")
    @ApiModelProperty(value = "角色拥有权限", dataType = "List")
    private List<Permission> permissionList;

    // 添加了mappedBy属性则不能使用@JoinTable注解
    @ManyToMany(mappedBy = "roleList")
    @ApiModelProperty(value = "角色拥有用户", dataType = "List")
    private List<User> userList;
}