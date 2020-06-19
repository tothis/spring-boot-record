package com.example.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TransactionConfig {

    /**
     * 事务拦截器
     */
    @Bean
    public TransactionInterceptor interceptor(PlatformTransactionManager manager) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        // 只读事务
        RuleBasedTransactionAttribute readOnlyAttr = new RuleBasedTransactionAttribute();
        readOnlyAttr.setReadOnly(true);

        // 不只读事务
        RuleBasedTransactionAttribute requiredAttr = new RuleBasedTransactionAttribute();
        // 设置回滚捕获异常
        requiredAttr.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredAttr.setTimeout(5);

        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("insert*", requiredAttr);
        txMap.put("delete*", requiredAttr);
        txMap.put("update*", requiredAttr);
        txMap.put("save*", requiredAttr);
        txMap.put("select*", readOnlyAttr);
        txMap.put("find*", readOnlyAttr);
        source.setNameMap(txMap);
        return new TransactionInterceptor(manager, source);
    }

    @Bean
    public Advisor advisor(TransactionInterceptor interceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.example.service..*ServiceImpl.*(..))");
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}