package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

/**
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@Service
public class RuleService {

    private final KieBase kieBase;

    public RuleService(KieBase kieBase) {
        this.kieBase = kieBase;
    }

    public void run() {
        KieSession kieSession = kieBase.newKieSession();
        // 加载规则
        int rulesCount = kieSession.fireAllRules();
        log.info("已匹配{}条规则", rulesCount);
        kieSession.dispose();
    }
}