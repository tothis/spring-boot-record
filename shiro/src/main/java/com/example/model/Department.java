package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@ApiModel(value = "com.example.model.Department", description = "部门")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "department")
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户名称", dataType = "String")
    @Column(name = "department_name")
    private String departmentName;

    @Transient
    @ApiModelProperty(value = "父节点", dataType = "String")
    private Department department;

    @Transient
    @ApiModelProperty(value = "子节点", dataType = "List")
    private List<Department> departmentList;

    @OneToMany(mappedBy = "department")
    @ApiModelProperty(value = "用户", dataType = "List")
    private List<User> userList;
}