package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("用户")
@Data
@ToString(exclude = {"roleList", "department"})
@JsonIgnoreProperties("password")
@NamedEntityGraph(name = "User.findByUserName", attributeNodes = @NamedAttributeNode("roleList"))
@Entity
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户名称", dataType = "String")
    @NotBlank(message = "账户不能为空")
    @Length(min = 4, max = 15, message = "帐户名长度为4-15")
    @Column(nullable = false, unique = true, length = 40)
    private String userName;

    @ApiModelProperty(value = "用户昵称", dataType = "String")
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

    @ApiModelProperty("用户密码")
    @Column(nullable = false)
    private String password;

    @ApiModelProperty("部门")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @ApiModelProperty("角色")
    @JsonIgnoreProperties("userList") // 避免递归死循环
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roleList;
}