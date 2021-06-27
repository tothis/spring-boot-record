package com.example.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 李磊
 */
@Data
@ApiModel(value = "com.example.model.BaseEntity", description = "基本实体类")
public class BaseEntity implements Serializable {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标识 0正常(默认) 1删除")
    private Byte state;

    @TableField(exist = false)
    @ApiModelProperty(value = "起始页", example = "1")
    private Long pageNum;

    @TableField(exist = false)
    @ApiModelProperty(value = "页显示数量", example = "10")
    private Long pageSize;
}
