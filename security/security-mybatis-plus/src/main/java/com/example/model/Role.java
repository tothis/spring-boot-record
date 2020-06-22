package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:44
 * @description
 */
@ApiModel("角色")
@Data
public class Role extends BaseEntity {
    @ApiModelProperty("角色名称")
    private String roleName;
}