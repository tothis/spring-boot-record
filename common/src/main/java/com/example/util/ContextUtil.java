package com.example.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author 李磊
 */
@Component
public final class ContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    private ContextUtil() {
    }

    public static <T> T getBean(final Class<T> clazz) {
        return ContextUtil.context.getBean(clazz);
    }

    public static <T> T getBean(final String name) {
        return (T) ContextUtil.context.getBean(name);
    }

    public static String getProperty(final String key) {
        return ContextUtil.context.getEnvironment().getProperty(key);
    }

    /**
     * Spring 加载时，如果 Bean 实现 ApplicationContextAware 接口，Spring容器会在创建该Bean后
     * 调用该Bean的setApplicationContextAware()方法，并传入applicationContext。
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        ContextUtil.context = applicationContext;
    }
}
