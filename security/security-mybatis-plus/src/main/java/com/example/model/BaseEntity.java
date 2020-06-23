package com.example.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty("数据主键")
    private Long id;

    @ApiModelProperty("数据创建者")
    private Long createBy;

    @ApiModelProperty("数据创建时间")
    private LocalDateTime createDateTime;

    @ApiModelProperty("数据更新者")
    private Long updateBy;

    @ApiModelProperty("数据更新时间")
    private LocalDateTime updateDateTime;

    @TableLogic
    @ApiModelProperty("数据状态 0正常(默认) 1删除 2禁用 3锁定")
    private Byte state;

    @TableField(exist = false)
    @ApiModelProperty("起始页")
    private Long startRow;

    @TableField(exist = false)
    @ApiModelProperty("每页显示的数据数量")
    private Long pageSize;
}