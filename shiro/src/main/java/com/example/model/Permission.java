package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.List;

@ApiModel("权限")
@Data
@ToString(exclude = "roleList")
@Entity
public class Permission extends BaseEntity {

    @ApiModelProperty("权限名称")
    private String permissionName;

    @ApiModelProperty("访问url")
    private String url;

    @ApiModelProperty("权限类型 0 menu 1 botton")
    private Boolean type;

    @ApiModelProperty("权限标识 user:add")
    private String permission;

    @ApiModelProperty("上级id")
    private Long parentId;

    @ApiModelProperty("图标")
    private String icon;

    @ManyToMany(mappedBy = "permissionList")
    private List<Role> roleList;

    /**
     * 单表树形结构
     */
    @Transient
    private List<Permission> permissionList;
}