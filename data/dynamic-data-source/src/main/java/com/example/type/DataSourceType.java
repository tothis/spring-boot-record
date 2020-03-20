package com.example.type;

import com.example.config.DynamicDataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 运行时可见
@Target(ElementType.METHOD) // 可用在方法上
public @interface DataSourceType {
    String value() default DynamicDataSource.base;
}