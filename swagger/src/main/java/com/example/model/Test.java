package com.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李磊
 * @datetime 2020/3/1 1:05
 * @description
 */
@Data
@ApiModel(value = "com.example.model.Test", description = "测试")
public class Test {
    /**
     * value 字段说明
     * name 重写属性名字
     * dataType 重写属性类型
     * required 是否必填
     * hidden 隐藏
     * example 示例值
     */
    @ApiModelProperty(value = "字段描述", required = true)
    private String field1;
    @ApiModelProperty(/*name = "name", */ example = "字符串")
    private String field2;
}