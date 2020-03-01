package com.example.controller;

import com.example.model.Test;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李磊
 * @datetime 2020/3/1 1:00
 * @description
 */
@Api(tags = {"测试"/*, "test"*/})
@RequestMapping("test")
@RestController
public class TestController {

    /**
     * response设置ui的Example Value和Model显示内容 默认自动获取方法的返回值类型
     */
    // 注释方法
    @ApiOperation(value = "查询", response = double.class)
    // 注释responses
    @ApiResponses({@ApiResponse(code = 200, message = "删除成功", response = double.class)})
    @GetMapping
    public String get(HttpServletRequest request, String content) {
        String token = request.getHeader("token");
        return content;
    }

    // 注释参数写法一 使用方法注解注释
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field1", value = "字段1"),
            @ApiImplicitParam(name = "field2", value = "字段2")
    })
    @PostMapping
    public Test post(String field1, String field2) {
        return new Test() {{
            setField1(field1);
            setField2(field2);
        }};
    }

    // 注释参数写法二 使用参数注解注释
    @PutMapping
    public String put(
            /**
             * name为前端表单的name value为参数描述
             * 使用@ApiParam需要@RequestParam配合使用
             * 使用example配置示例值 空字符串无法解析为Long 且抛出NumberFormatException
             */
            @ApiParam(name = "id", value = "主键", example = "0") @RequestParam(required = false) Long id
    ) {
        return "put";
    }

    /**
     * 删除
     */
    // 注释参数写法三 使用ApiModel注释
    @DeleteMapping
    public Test delete(Test test) {
        return test;
    }
}