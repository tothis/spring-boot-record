package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 李磊
 * @datatime 2020-01-16
 * @description 实体
 */
@Data
@ApiModel(value = "com.example.model.User", description = "用户")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("类型")
    private String typeId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    /**
     * 年龄 byte(127/-128)
     */
    @ApiModelProperty("年龄")
    private Byte age;

    @ApiModelProperty("邮箱")
    private String mail;

    @ApiModelProperty("生日")
    private LocalDateTime birthday;
}