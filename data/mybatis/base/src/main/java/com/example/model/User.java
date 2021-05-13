package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李磊
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
    private List<Address> addresses;

    @ApiModelProperty("类型")
    private Type type;

    @Data
    // 被mybatis映射结果集需声明为static
    @ApiModel("地址")
    static class Address {
        private Long id;
        private Long userId;
        private String detail;
    }

    @Data
    @ApiModel("类型")
    static class Type {
        private Long id;
        private String detail;
    }
}