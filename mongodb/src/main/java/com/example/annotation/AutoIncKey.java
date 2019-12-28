package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 标识主键字段需要自动增长
 * <p>
 * ClassName: AutoIncKey
 * </p>
 * <p>
 * Copyright: (c)2019 lilei,All rights reserved.
 * </p>
 *
 * @author 李磊
 * @time 2019/11/10 17:52
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIncKey {
}