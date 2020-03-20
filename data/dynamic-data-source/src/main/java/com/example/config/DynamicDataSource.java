package com.example.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final String base = "center";

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceSwitch.get();
    }
}