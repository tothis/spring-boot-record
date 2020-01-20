package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ApiModel(value = "com.example.model.Permission", description = "资源权限")
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据主键", dataType = "Long")
    @Id // 标识主键
    @Column(name = "id", columnDefinition = "bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键'")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    @ApiModelProperty(value = "资源名称", dataType = "String")
    @Column(name = "permission_name")
    private String permissionName;

    @ApiModelProperty(value = "资源路径", dataType = "String")
    @Column(name = "url")
    private String url;

    @ApiModelProperty(value = "资源类型  1resource 2menu 3botton", dataType = "Byte")
    @Column(name = "type")
    private Byte type;

    @ApiModelProperty(value = "权限标识 user:add", dataType = "String")
    @Column(name = "permission")
    private String permission;

    @ApiModelProperty(value = "父资源", dataType = "Integer")
    @Column(name = "parent_id")
    private Long parentId;

    @ApiModelProperty(value = "排序", dataType = "Integer")
    @Column(name = "sort")
    private Byte sort;

    @ApiModelProperty(value = "图标", dataType = "Byte")
    @Column(name = "icon")
    private String icon;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * 单表树形结构
     */
    @Transient
    private List<Permission> permissionList;
}
