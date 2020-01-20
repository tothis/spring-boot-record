package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "com.example.model.BaseEntity", description = "基本实体类")
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 此注解可被继承 即继承此类的类此注解依然生效
@JsonIgnoreProperties(ignoreUnknown = true, value = "delFlag")
@MappedSuperclass // 实体继承jpa映射
// @SequenceGenerator(name = "tb_user_sq", sequenceName = "tb_user_sqe")
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(generator = "tb_user_sq", strategy = GenerationType.SEQUENCE)
//    private Long id;

    @ApiModelProperty(value = "数据主键", dataType = "Long")
    @Id // 标识主键
    // 使用columnDefinition后 hibernate不会指定数据库字段类型
    @Column(name = "id", columnDefinition = "bigint(20) COMMENT '主键'")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    @ApiModelProperty(value = "数据创建者", dataType = "Long")
    private Long createBy;

    @ApiModelProperty(value = "数据创建时间", dataType = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createTime;

    @ApiModelProperty(value = "数据更新者", dataType = "Long")
    private Long updateBy;

    @ApiModelProperty(value = "数据更新时间", dataType = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除 0未删除(默认) 1已删除", dataType = "Boolean")
    // @AssertFalse
    // @AssertTrue
    private Boolean delFlag;

    @ApiModelProperty(value = "是否启用 0不启用 1启用(默认)", dataType = "Boolean")
    private Boolean state;

    @ApiModelProperty(value = "起始页", dataType = "Long")
    private Long startRow;

    @ApiModelProperty(value = "每页显示的数据数量", dataType = "Long")
    private Long pageSize;
}