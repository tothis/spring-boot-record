package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

@ApiModel("部门")
@Data
@Entity
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @Transient
    @ApiModelProperty("父节点")
    private Department department;

    @Transient
    @ApiModelProperty("子节点")
    private List<Department> departmentList;

    @ApiModelProperty("部门下的用户")
    @OneToMany(mappedBy = "department")
    private List<User> userList;
}