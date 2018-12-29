package com.denachina.shadow.dbconfig;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class BaseConfig {

    public static DataSource createDataSource(String url, String username, String password) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setMinimumIdle(1);
//        dataSource.setMaximumPoolSize(10);
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setLeakDetectionThreshold(2000);
        dataSource.setAutoCommit(false);
        // 调优参数
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return dataSource;
    }
}
