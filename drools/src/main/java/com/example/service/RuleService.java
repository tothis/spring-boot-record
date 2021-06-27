package com.example.service;

import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

/**
 * @author 李磊
 */
@Slf4j
@Service
public class RuleService {

    private final KieContainer container;

    public RuleService(KieContainer container) {
        this.container = container;
    }

    public void run() {
        KieSession kieSession = container.newKieSession();
        User user = new User();
        user.setName("lilei");
        user.setAge(20);

        kieSession.insert(user);
        // 加载规则
        int rulesCount = kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("成年"));
        log.info("已匹配{}条规则", rulesCount);
        log.info("{}", user);
        kieSession.dispose();
    }
}
