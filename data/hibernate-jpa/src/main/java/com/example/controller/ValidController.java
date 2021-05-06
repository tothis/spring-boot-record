package com.example.controller;

import com.example.model.ValidTest;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 参收中使用校验注解如@Range 当前类需要添加@Validated
 *
 * @author 李磊
 */
@Validated
@RequestMapping("valid")
@RestController
public class ValidController {

    @PostMapping("1")
    public void valid1(@RequestBody @Valid ValidTest r) {
        System.out.println(r);
    }

    @GetMapping("2")
    public void valid2(@Range(min = 1, max = 120, message = "年龄为1-120") int age,
                       @Min(value = 1, message = "排序最小为1")
                       @Max(value = 100, message = "排序最大为100") int sort
    ) {
        System.out.println(age + ' ' + sort);
    }

    @PostMapping("3")
    public void valid3(@RequestBody @Valid
                       @Size(min = 2, message = "数据长度最小为2")
                       @NotEmpty(message = "数据不可为空")
                               List<ValidTest> r) {
        System.out.println(r);
    }

    @PostMapping("group")
    public void group(@RequestBody @Validated(ValidTest.Update.class) ValidTest r) {
        System.out.println(r);
    }
}