package com.example.aop;

import com.example.config.DataSourceSwitch;
import com.example.config.DynamicDataSource;
import com.example.type.DataSourceType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author 李磊
 * @datetime 2020/03/18 22:14
 * @description
 */
@Component
@Aspect
public class DataSourceAspect {

    @Before("@annotation(type)")
    public void changeDataSource(JoinPoint point, DataSourceType type) {
        String dataSourceId = type.value();
        if (DataSourceSwitch.isExist(dataSourceId)) {
            DataSourceSwitch.set(dataSourceId);
            System.out.println("\033[36m数据源已切换[" + dataSourceId + "] - " + point.getSignature() + "\033[m");
        } else {
            System.out.println("\033[31m数据源不存在[" + dataSourceId + "] - " + point.getSignature() + "\033[m");
        }
    }

    @After("@annotation(type)")
    public void reset(JoinPoint point, DataSourceType type) {
        DataSourceSwitch.reset();
        System.out.println("\033[34m数据源已还原[" + DynamicDataSource.base + "] - " + point.getSignature() + "\033[m");
    }
}