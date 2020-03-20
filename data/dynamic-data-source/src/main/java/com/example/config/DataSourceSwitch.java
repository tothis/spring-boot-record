package com.example.config;

/**
 * 线程安全的DataSourceEnum容器 并提供设置和获取DataSourceEnum函数
 */
public class DataSourceSwitch {

    private static final ThreadLocal<String> DATA_SOURCE = ThreadLocal.withInitial(() -> DynamicDataSource.base);

    public static void set(String type) {
        DATA_SOURCE.set(type);
    }

    public static String get() {
        return DATA_SOURCE.get();
    }

    public static void reset() {
        DATA_SOURCE.set(DynamicDataSource.base);
    }
}