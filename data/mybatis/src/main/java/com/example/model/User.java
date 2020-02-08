package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    @ApiModelProperty("密码")
    private String password;

    /**
     * 年龄 byte(127/-128)
     */
    @ApiModelProperty(value = "年龄", dataType = "Byte")
    private Byte age;

    @ApiModelProperty(value = "邮箱", dataType = "String")
    private String mail;

    @ApiModelProperty(value = "生日", dataType = "LocalDateTime")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "地址", dataType = "Address")
    private List<Address> addressList;

    @Data
    // 被mybatis映射结果集需声明为static
    static class Address {
        private Long id;
        private String detail;
    }
}