package com.example.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude = {"userList", "permissionList"})
/**
 * @NamedEntityGraph解决N+1查询问题
 * 执行jpa提供的find方法时 如果查询出来对象关联着另外10个对象
 * jpa将会发送1+10次查询 当前对象本身查询一次 每个关联对象查询一次
 * 如有100个用户发送请求 会产生1100次查询 可通过@NamedEntityGraph避免此问题
 * name @EntityGraph调用名称 默认为当前实体名
 * attributeNodes 关联属性名称
 */
@NamedEntityGraph(name = "Role.findAll", attributeNodes = @NamedAttributeNode("userList"))
@NamedEntityGraph(attributeNodes = @NamedAttributeNode("userList"))
@Entity
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    private String roleName;

    /**
     * 使用@JoinTable注解添加中间表
     * name 中间表表名
     * joinCloums 当前表映射列
     * inverseJoinColumns 用户表表映射列
     * name 列名
     * referencedColumnName 被映射表字段 关联字段为主键时可省略
     * <p>
     * 标准用法一方使用@ManyToMany+@JoinTable 另一方使用@ManyToMany(mappedBy = "xxxx")
     * 使用mappedBy方等于放弃维护机会即 使用mappedBy属于被控方 中间表数据只能由主控方删除
     * <p>
     * 删除角色时只会删除`角色表`和`角色用户关联表`数据 设置级联删除后 会删除user
     */
    // @JsonIgnoreProperties("roleList")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role", // 用户和角色关联表名称 不指定时默认名称为'表名1'+'_'+'表名2'
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> userList; // 角色拥有用户

    // @JsonIgnoreProperties("roleList")
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissionList;
}