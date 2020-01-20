package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "com.example.model.User", description = "用户")
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true, value = "password")
@Entity/*(name = "user")*/
@Table(name = "user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户名称", dataType = "String")
    @NotBlank(message = "账户不能为空")
    @Length(min = 4, max = 15, message = "帐户名长度为4-15")
    @Column(name = "user_name", nullable = false, unique = true, length = 40)
    private String userName;

    @ApiModelProperty(value = "用户昵称", dataType = "String")
    @NotBlank(message = "昵称不能为空")
    @Column(name = "nick_name", length = 40)
    private String nickName;

    @ApiModelProperty(value = "邮箱", dataType = "String")
    @Column(name = "mail", length = 40)
    private String mail;

    @ApiModelProperty(value = "年龄", dataType = "Byte")
    @Range(min = 18, max = 60, message = "年龄不合法")
    @Column(name = "age")
    private Byte age;

    @ApiModelProperty(value = "生日", dataType = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Past(message = "生日必须是一个过去的日期") // 被注释的元素必须是一个过去的日期
    // 实体属性不加@Basic注解 也会自动加上@Basic 并使用默认值
    @Basic(fetch = FetchType.LAZY, optional = true) // 是否懒加载 是否允许为空
    @Column(name = "birthday")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "加密盐值", dataType = "String")
    @Column(name = "salt", length = 36)
    private String salt;

    @ApiModelProperty(value = "用户密码", dataType = "String")
    @Column(name = "password", length = 40)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    // 默认@Basic会将属性名和数据库表关联使用@Transient取消关联
    // @Transient
    // @Size(min = 1, max = 10)

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     * 将用户对象或部门对象转为json数据时 为防止出现无限循环包含对方 需在一方引用对方对象set方法 加上注解@JsonBackReference
     */
    @JsonBackReference
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * 使用@JoinTable注解添加中间表
     * name 中间表表名
     * joinCloums 中间表中添加列
     * inverseJoinColumns 另外一张表在中间表中的列
     * name 列名
     * referencedColumnName 被映射表主键 可省略该属性
     * <p>
     * 两个多对多直接使用@ManyToMany且不使用@JoinTable和mappedBy则直接生成两个关联表
     * 正确用法一方使用@ManyToMany+@JoinTable 另一方使用@ManyToMany(mappedBy = "xxxx")
     * 使用mappedBy方等于放弃维护机会即 使用mappedBy属于被控方 中间表数据只能由主控方删除
     */
    @ManyToMany
    @JoinTable(
            name = "user_role", // 用户和角色关联表名称 不指定时默认名称为'表名1'+'_'+'表名2'
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roleList;
}
