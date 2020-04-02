package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties("delFlag")
@MappedSuperclass // 实体继承jpa映射
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    @ApiModelProperty("数据创建者")
    private Long createBy;

    @ApiModelProperty("数据创建时间")
    @CreationTimestamp
    private LocalDateTime createTime;

    @ApiModelProperty("数据更新者")
    private Long updateBy;

    @ApiModelProperty("数据更新时间")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @ApiModelProperty("数据状态 0正常(默认) 1删除 2禁用")
    @Column(name = "state", columnDefinition = "tinyint(1) DEFAULT '0' COMMENT '数据状态 0正常(默认) 1删除 2禁用'")
    private Byte state;

    @ApiModelProperty("起始页")
    private Long startRow;

    @ApiModelProperty("每页显示的数据数量")
    private Long pageSize;
}