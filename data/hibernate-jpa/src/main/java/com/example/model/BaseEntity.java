package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 李磊
 * @datetime 2020/3/4 12:45
 * @description
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 此注解可被继承 即继承此类的类此注解依然生效
@JsonIgnoreProperties(ignoreUnknown = true, value = "delFlag")
@MappedSuperclass // 实体继承jpa映射
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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

    // 实体属性不加@Basic注解 也会自动加上@Basic 并使用默认值
    // 默认@Basic会将属性名和数据库表关联使用@Transient取消关联

    @Id // 标识主键
    // 使用columnDefinition后 hibernate不会指定数据库字段类型
    @Column(name = "id", columnDefinition = "bigint(20) COMMENT '主键'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bigint(20) COMMENT '数据创建者'")
    private Long createBy;

    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(columnDefinition = "bigint(20) COMMENT '数据更新者'")
    private Long updateBy;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    // @FutureDate(1)
    // private Date date;

    // @AssertFalse
    // @AssertTrue
    @Column(name = "is_del", columnDefinition = "bit(1) DEFAULT b'0' COMMENT '是否删除 0正常(默认) 1删除'")
    private Boolean delFlag;
}