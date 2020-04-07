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

/**
 * @author 李磊
 * @datetime 2020/4/8 17:48
 * @description
 */
@Configuration
public class TransactionConfig {

    /**
     * 事务拦截器
     */
    @Bean
    public TransactionInterceptor interceptor(PlatformTransactionManager manager) {

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        // 只读事务 不做更新操作
        RuleBasedTransactionAttribute readOnlyAttr = new RuleBasedTransactionAttribute();
        // 设置传播行为 默认PROPAGATION_REQUIRED 当前存在事务就使用当前事务 当前不存在事务就创建一个新的事务
        // readOnlyAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnlyAttr.setReadOnly(true);

        // 不只读事务
        // RuleBasedTransactionAttribute requiredAttr = new RuleBasedTransactionAttribute(
        //         TransactionDefinition.PROPAGATION_REQUIRED
        //         , Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        RuleBasedTransactionAttribute requiredAttr = new RuleBasedTransactionAttribute();
        // 设置回滚捕获异常
        requiredAttr.setRollbackRules(Collections
                .singletonList(new RollbackRuleAttribute(Exception.class)));

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
        /**
         * execution() 表达式主体
         * 第一个`*`	返回值类型任意
         * `..` 当前包及子包
         * *ServiceImpl	以`ServiceImpl`结尾的类
         * *(..) `*`任何方法名 `()`参数 `(..)`表示任意参数类型
         */
        pointcut.setExpression("execution(* com.example..service.impl.*ServiceImpl.*(..))");
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }

    // 另一种配置方式
    /*@Bean("txAdvice")
    public TransactionInterceptor interceptor(PlatformTransactionManager manager) {
        Properties properties = new Properties();
        properties.setProperty("insert*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("select*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("find*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        return new TransactionInterceptor(manager, properties);
    }

    @Bean
    public BeanNameAutoProxyCreator txProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("txAdvice");
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }*/
}