package com.denachina.shadow.dbconfig;

public class PostgresDBContextHolder {
    public static final String POSTGRES_R = "postgresRDataSource";
    public static final String POSTGRES_W = "postgresWDataSource";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDBType() {
        return contextHolder.get();
    }

    public static void clearDBType() {
        contextHolder.remove();
    }
}
