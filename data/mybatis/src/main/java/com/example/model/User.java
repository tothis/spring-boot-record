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

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    /**
     * 年龄 byte(127/-128)
     */
    @ApiModelProperty(value = "年龄", example = "0")
    private Byte age;

    @ApiModelProperty("邮箱")
    private String mail;

    @ApiModelProperty("生日")
    private LocalDateTime birthday;

    @ApiModelProperty("地址")
    private List<Address> addressList;

    @Data
    // 被mybatis映射结果集需声明为static
    @ApiModel(value = "com.example.model.User$Address", description = "用户地址")
    static class Address {
        @ApiModelProperty(example = "1")
        private Long id;
        private String detail;
    }
}