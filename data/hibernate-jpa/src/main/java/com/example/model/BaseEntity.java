package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Id // 标识主键
    // 使用columnDefinition后 hibernate不会指定数据库字段类型
    @Column(name = "id", columnDefinition = "bigint(20) COMMENT '主键'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bigint(20) COMMENT '数据创建者'")
    private Long createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(columnDefinition = "bigint(20) COMMENT '数据更新者'")
    private Long updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    // @Date("yyyy-MM-dd")
    // private String date;

    // @AssertFalse
    // @AssertTrue
    @Column(name = "is_del", columnDefinition = "bit(1) DEFAULT b'0' COMMENT '是否删除 0正常(默认) 1删除'")
    private Boolean delFlag;
}