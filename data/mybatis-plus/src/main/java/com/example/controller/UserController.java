package com.example.controller;

import com.example.model.User;
import com.example.service.IUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李磊
 * @datetime 2020-01-16
 * @description 控制层
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private IUserService iUserService;

    @ApiOperation("增加用户")
    @PostMapping("add")
    public int add(@RequestBody User entity) {
        return iUserService.insert(entity);
    }

    /**
     * 删除
     */
    @ApiOperation("删除数据")
    @ApiResponses({@ApiResponse(code = 0, message = "删除用户", response = int.class)})
    @GetMapping("delete")
    public int delete(@ApiParam(value = "用户id", required = true)@RequestParam("id") Long id) {
        return iUserService.deleteById(id);
    }

    /**
     * 查询
     */
    /**
     * name 参数名 对应方法中单独的参数名称
     * value 参数中文说明
     * required 是否必填
     * paramType 参数类型 取值为path, query, body, header, form
     * dataType 参数数据类型
     * defaultValue 默认值
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long")
    })
    @ApiOperation("查询单个用户")
    @GetMapping("selectById")
    public User selectById(@RequestParam("id") Long id) {
        return iUserService.selectById(id);
    }
}
