package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李磊
 */
@ApiModel("角色")
@Data
public class Role extends BaseEntity {

    @ApiModelProperty("角色名称")
    private String roleName;
}