package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:19
 * @description 用户
 */
@Data
@ToString(exclude = {"roleList", "department"})
@Entity // jpa检测到@Entity时 会在数据库中生成对应表结构
@Table(name = "user")
public class User extends BaseEntity {

    @NotBlank(message = "用户名不能为空")
    @Column(name = "user_name")
    private String userName;

    @Length(min = 8, max = 16, message = "密码长度在[8-16]之间")
    private String password;

    // @Pattern(regexp = "^(?:[1-9][0-9]?|1[01][0-9]|120)$", message = "年龄在1-120")
    // @Range(min = 1, max = 120, message = "年龄不合法")
    @Max(value = 120, message = "年龄最大为120")
    @Min(value = 1, message = "年龄最小为1")
    private Byte age;

    private String mail;

    @Past(message = "生日必须是一个过去的日期") // 被注释的元素必须是一个过去的日期
    @Column(name = "birthday")
    private LocalDateTime birthday;

    // user为关系维护端 级联保存 删除address
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    // user中address_id字段参考address表中的id字段
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // 序列化时忽略department的userList属性
    @JsonIgnoreProperties("userList")
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     * @Validated 可用在类型 方法 方法参数上 不可用在成员属性上 无法进行嵌套验证 但可配合嵌套验证注解@Valid嵌套验证
     * @Valid 可用在方法 构造函数 方法参数和成员属性上 可进行嵌套验证
     */
    @Valid // 嵌套验证必须用@Valid
    // 添加了mappedBy属性则不能使用@JoinTable注解
    // @JsonIgnoreProperties("userList")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roleList;
}