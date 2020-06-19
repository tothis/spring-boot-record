package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
// 属性为null或为空字符串时不返回前端 此注解可被继承
@JsonInclude(JsonInclude.Include.NON_EMPTY)
// @JsonIgnoreProperties("state") // 被子类继承后 此配置失效
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
    private LocalDateTime createDateTime;

    @ApiModelProperty("数据更新者")
    private Long updateBy;

    @ApiModelProperty("数据更新时间")
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @ApiModelProperty("数据状态 0正常(默认) 1删除 2禁用 3锁定")
    @JsonIgnore
    @Column(name = "state", columnDefinition = "tinyint(1) DEFAULT '0' COMMENT '数据状态 0正常(默认) 1删除 2禁用'")
    private Byte state = 0;

    @ApiModelProperty("起始页")
    private Long startRow;

    @ApiModelProperty("每页显示的数据数量")
    private Long pageSize;
}