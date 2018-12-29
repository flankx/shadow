package com.denachina.shadow.util;

import com.denachina.shadow.dbconfig.PostgresDBContextHolder;

public class DbUtil {

    public static final void setPostgresDbR() {
        PostgresDBContextHolder.setDBType(PostgresDBContextHolder.POSTGRES_R);
    }

    public static final void setPostgresDbW() {
        PostgresDBContextHolder.setDBType(PostgresDBContextHolder.POSTGRES_W);
    }
}
