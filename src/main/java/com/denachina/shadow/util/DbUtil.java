package com.denachina.shadow.util;

import com.denachina.shadow.config.dbconfig.DBContextHolder;

public class DbUtil {

    public static void setDbR() {
        DBContextHolder.setDBType(DBContextHolder.R);
    }

    public static void setDbW() {
        DBContextHolder.setDBType(DBContextHolder.W);
    }
}
