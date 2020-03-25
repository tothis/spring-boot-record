package com.example.aop;

import com.example.config.DataSourceSwitch;
import com.example.type.DataSourceEnum;
import com.example.type.DataSourceType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DataSourceAspect {
    @Before("@annotation(type)")
    public void changeDataSource(JoinPoint point, DataSourceType type) {
        DataSourceEnum value = type.value();
        DataSourceSwitch.set(value);
        System.out.println("\033[36m数据源已切换[" + value + "] - " + point.getSignature() + "\033[m");
    }

    @After("@annotation(type)")
    public void reset(JoinPoint point, DataSourceType type) {
        DataSourceSwitch.reset();
        System.out.println("\033[34m数据源已还原[" + DataSourceEnum.db1 + "] - " + point.getSignature() + "\033[m");
    }
}