package com.example.controller;

import com.example.entity.ResultEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 李磊
 */
@RestController
public class TestController {
    @GetMapping
    Map get(@RequestParam Map param) {
        return param;
    }

    @PostMapping
    Map post(@RequestBody Map param) {
        return param;
    }

    @GetMapping("get/{value}")
    ResultEntity get(@PathVariable String value) {
        return ResultEntity.ok(value);
    }

    @PostMapping("post/{value}")
    ResultEntity post(@PathVariable String value) {
        return ResultEntity.ok(value);
    }
}
