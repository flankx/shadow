package com.denachina.shadow.dbconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class PostgresDynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return PostgresDBContextHolder.getDBType();
    }
}
