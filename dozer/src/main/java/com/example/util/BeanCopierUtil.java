package com.example.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李磊
 * @datetime 2020/3/25 16:45
 * @description bean转换类
 */
public class BeanCopierUtil {

    /**
     * 使用Map存储BeanCopier实例 避免不需要的实例创建
     */
    private static final Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    /**
     * @param source 资源类
     * @param target 目标类
     */
    public static void copy(Object source, Object target) {
        if (source == null || target == null)
            throw new NullPointerException("资源对象或目标对象不能为null");

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        String beanKey = key(sourceClass, targetClass);
        BeanCopier copier;
        if (beanCopierMap.containsKey(beanKey)) {
            copier = beanCopierMap.get(beanKey);
        } else {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            beanCopierMap.put(beanKey, copier);
        }
        copier.copy(source, target, null);
    }

    public static <T> T convert(Object source, Class<T> clazz) {
        if (source == null || clazz == null)
            throw new NullPointerException("参数不能为null");

        Class<?> sourceClass = source.getClass();
        String beanKey = key(sourceClass, clazz);
        BeanCopier copier;
        if (beanCopierMap.containsKey(beanKey)) {
            copier = beanCopierMap.get(beanKey);
        } else {
            copier = BeanCopier.create(sourceClass, clazz, false);
            beanCopierMap.put(beanKey, copier);
        }
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        copier.copy(source, result, null);
        return result;
    }

    private static String key(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }
}