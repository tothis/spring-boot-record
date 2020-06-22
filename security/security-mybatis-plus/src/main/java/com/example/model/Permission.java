package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:45
 * @description
 */
@ApiModel("权限")
@Data
public class Permission extends BaseEntity {
    @ApiModelProperty("权限名称")
    private String permissionName;

    @ApiModelProperty("权限标识")
    private String permissionFlag;

    @ApiModelProperty("接口地址")
    private String url;
}