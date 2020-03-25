package com.example.type;

import com.example.config.DynamicDataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 李磊
 * @datetime 2020/03/18 22:20
 * @description
 */
@Retention(RetentionPolicy.RUNTIME) // 运行时可见
@Target(ElementType.METHOD) // 可用在方法上
public @interface DataSourceType {
    String value() default DynamicDataSource.base;
}