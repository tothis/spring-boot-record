package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("用户")
@Data
@JsonIgnoreProperties("password")
@Entity
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户名称", dataType = "String")
    @NotBlank(message = "账户不能为空")
    @Length(min = 4, max = 15, message = "帐户名长度为4-15")
    @Column(nullable = false, unique = true, length = 40)
    private String userName;

    @ApiModelProperty(value = "用户昵称", dataType = "String")
    @NotBlank(message = "昵称不能为空")
    @Column(length = 40)
    private String nickName;

    @ApiModelProperty(value = "邮箱", dataType = "String")
    private String mail;

    @ApiModelProperty("年龄")
    @Range(min = 0, max = 120, message = "年龄不合法")
    private Byte age;

    @ApiModelProperty("生日")
    @Past(message = "生日必须是一个过去的日期")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "加密盐值", hidden = true)
    private String salt;

    @ApiModelProperty(value = "用户密码", hidden = true)
    private String password;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "userList")
    private List<Role> roleList;
}