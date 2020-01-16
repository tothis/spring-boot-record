package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李磊
 * @datatime 2020-01-16
 * @description 实体
 */
@Data
@ApiModel(value = "com.example.model.User", description = "用户")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名称", dataType = "String")
    private String userName;

    /**
     * 年龄 byte(127/-128)
     */
    @ApiModelProperty(value = "年龄", dataType = "Byte")
    private Byte age;

    @ApiModelProperty(value = "地址", dataType = "String")
    private String address;

    @ApiModelProperty("密码")
    private String password;

}