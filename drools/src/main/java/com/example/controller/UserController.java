package com.example.controller;

import com.example.service.RuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李磊
 * @since 1.0
 */
@RequestMapping("user")
@RestController
public class UserController {

    private final RuleService ruleService;

    public UserController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping
    public void test() {
        ruleService.run();
    }
}