package com.example.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring工具
 *
 * @author 李磊
 */
@Component
public final class ContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        return ContextUtil.applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name) {
        return (T) ContextUtil.applicationContext.getBean(name);
    }

    public static String getProperty(String key) {
        return ContextUtil.applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * Spring加载时 如果Bean实现ApplicationContextAware接口 Spring容器会在创建该Bean后
     * 调用该Bean的setApplicationContextAware()方法 并传入applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtil.applicationContext = applicationContext;
    }
}