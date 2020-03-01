package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 李磊
 * @datetime 2020/1/11 18:00
 * @description
 */
@Data
@ApiModel(value = "com.example.model.BaseEntity", description = "基本实体类")
public class BaseEntity implements Serializable {
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    @ApiModelProperty("删除标识 0正常(默认) 1删除")
    private Boolean delFlag;
}