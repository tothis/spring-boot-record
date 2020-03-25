package com.example.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author 李磊
 * @datetime 2020/03/18 22:20
 * @description
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final String base = "center";

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceSwitch.get();
    }
}