package com.example.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author 李磊
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final String BASE = "center";

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceSwitch.get();
    }
}
