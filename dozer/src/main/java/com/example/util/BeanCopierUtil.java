package com.example.util;

import net.sf.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

public class BeanCopierUtil {

    public static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    /**
     * @param source 资源类
     * @param target 目标类
     */
    public static void copy(Object source, Object target) {
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

    private static String key(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }
}