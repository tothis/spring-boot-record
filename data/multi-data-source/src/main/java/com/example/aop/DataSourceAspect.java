package com.example.aop;

import com.example.config.DataSourceSwitch;
import com.example.type.DataSourceEnum;
import com.example.type.DataSourceType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class DataSourceAspect {
    @Pointcut("execution(* com.example..*(..)) && @annotation(com.example.type.DataSourceType)")
    public void dataSourcePointcut() {
    }

    @Around("dataSourcePointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DataSourceType sourceType = method.getAnnotation(DataSourceType.class);
        switch (sourceType.value()) {
            case db1:
                DataSourceSwitch.set(DataSourceEnum.db1);
                break;
            case db2:
                DataSourceSwitch.set(DataSourceEnum.db2);
            default:
                break;
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            DataSourceSwitch.reset();
        }
        return result;
    }
}