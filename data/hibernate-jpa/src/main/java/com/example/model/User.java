package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:19
 * @description 用户
 */
@Data
@NoArgsConstructor
// jpa检测到@Entity时 会在数据库中生成对应表结构
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    // @Id
    // // generator jpa中唯一标识
    // @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_sq")
    // @TableGenerator(name = "user_sq" // 和GeneratedValue的generator值一致
    //         , table = "tb_user_sq" // 生成表表名
    //         , pkColumnName = "description" // 表中保存描述字段名
    //         , pkColumnValue = "用户 user" // 表中保存描述值
    //         , valueColumnName = "sequence_value" // 表中保存主键值字段名
    //         , allocationSize = 1 // 自增值
    // )
    // private Long id;

    // @Id
    // // generator jpa中唯一标识
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sq")
    // @SequenceGenerator(
    //         name = "user_sq" // 和GeneratedValue的generator值一致
    //         , sequenceName = "tb_user_sqe" // 生成表表名
    //         , allocationSize = 1 // 自增值
    // )
    // private Long id;

    // @Id // 标识主键
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    // private Long id;

    @NotBlank(message = "用户名不能为空")
    @Column(name = "user_name")
    private String userName;

    @Length(min = 8, max = 16, message = "密码长度在[8-16]之间")
    private String password;

    // @Pattern(regexp = "^(?:[1-9][0-9]?|1[01][0-9]|120)$", message = "年龄在1-120")
    @Max(value = 120, message = "年龄最大为120")
    @Min(value = 1, message = "年龄最小为1")
    private Byte age;

    private String mail;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

    // user为关系维护端 级联保存 删除address
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    // user中address_id字段参考address表中的id字段
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", mail='" + mail + '\'' +
                ", birthday=" + birthday +
                ", address=" + address +
                '}';
    }
}