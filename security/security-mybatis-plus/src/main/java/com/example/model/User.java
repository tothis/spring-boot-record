package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李磊
 * @datetime 2020/6/22 0:44
 * @description
 */
@ApiModel("用户")
@Data
public class User extends BaseEntity {
    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户密码")
    private String password;
}