package com.example.util;

import com.example.model.User;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李磊
 * @datetime 2020/3/25 16:45
 * @description bean转换类 只可copy同名同类型字段
 */
public class BeanCopierUtil {

    /**
     * 使用Map存储BeanCopier实例 避免不需要的实例创建
     */
    private static final Map<String, BeanCopier> beanCopierMap = new HashMap<>();
    private static final Map<String, String[]> methodMap = new HashMap<>();

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
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanCopierUtil.copy(source, result);
        return result;
    }

    /**
     * @param source 资源类
     */
    public static <T> List<T> convertList(List source, Class<T> targetClass) {
        if (source == null || source.isEmpty())
            throw new NullPointerException("资源对象不能为空");
        List<T> list = new ArrayList<>();
        for (Object item : source) {
            list.add(BeanCopierUtil.convert(item, targetClass));
        }
        return list;
    }

    private static String key(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    /**
     * @param bean
     * @description 使bean中为null的属性转换成空字符串
     */
    public static void nullToEmpty(Object bean) {
        Class beanClass = bean.getClass();
        Field[] field = beanClass.getDeclaredFields();
        for (int i = 0; i < field.length; i++) { // 遍历所有属性
            String fieldName = field[i].getName(); // 获取属性名称
            // 将属性的首字符大写 方便构造get set方法
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String type = field[i].getGenericType().toString(); // 获取属性类型
            if (type.equals("class java.lang.String")) { // 如果属性为字符串
                try {
                    String getName = "get" + fieldName;
                    if (isExistMethod(beanClass, getName)) {
                        Object invoke = beanClass.getMethod(getName).invoke(bean); // 调用get方法获取属性值
                        if (invoke == null) {
                            String setName = "set" + fieldName;
                            if (isExistMethod(beanClass, setName))
                                // 字符串属性为null时 把该属性值设为空字符串
                                beanClass.getMethod(setName, String.class).invoke(bean, "");
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isExistMethod(Class clazz, String methodName) {
        String clazzName = clazz.getName();
        String[] methods;
        if (methodMap.containsKey(clazzName)) {
            methods = methodMap.get(clazzName);
        } else {
            Method[] clazzMethods = clazz.getMethods();
            if (clazzMethods == null && clazzMethods.length == 0) return false;
            methods = new String[clazzMethods.length];
            for (int i = 0; i < clazzMethods.length; i++) {
                methods[i] = clazzMethods[i].getName();
            }
            methodMap.put(clazzName, methods);
        }
        for (String method : methods)
            if (method.equals(methodName))
                return true;
        return false;
    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        User user = new User();
        for (int i = 0; i < 1000_000; i++) {
            nullToEmpty(user);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }
}