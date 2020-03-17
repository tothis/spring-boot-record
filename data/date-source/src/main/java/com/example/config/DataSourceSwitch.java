package com.example.config;

import com.example.type.DataSourceEnum;

/**
 * 线程安全的DataSourceEnum容器 并提供设置和获取DataSourceEnum函数
 */
public class DataSourceSwitch {

    private static final ThreadLocal<DataSourceEnum> DATA_SOURCE = ThreadLocal.withInitial(() -> DataSourceEnum.db1);

    public static void set(DataSourceEnum type) {
        DATA_SOURCE.set(type);
    }

    public static DataSourceEnum get() {
        return DATA_SOURCE.get();
    }

    public static void reset() {
        DATA_SOURCE.set(DataSourceEnum.db1);
    }
}