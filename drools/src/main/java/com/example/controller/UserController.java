package com.example.controller;

import com.example.entity.User;
import com.example.service.RuleService;
import com.example.util.DroolsUtil;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
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

    @GetMapping("excel")
    public void excel() {
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        Resource resource = ResourceFactory.newClassPathResource("decision.xlsx");
        String drlContent = compiler.compile(resource, InputType.XLS);
        System.out.println(drlContent);

        KieSession kieSession = DroolsUtil.createKieSessionByDrlContent(drlContent);

        User user = new User();
        user.setName("lilei");
        user.setAge(20);

        // 将user作为参数传入drools执行
        kieSession.insert(user);
        // 加载规则 不设置过滤器则触发所有规则
        System.out.println(kieSession.fireAllRules());
        System.out.println(user.getName());

        // 关闭会话
        kieSession.dispose();
    }
}