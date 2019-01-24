package com.denachina.shadow.config.dbconfig;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class BaseConfig {

    /**
     *
     * minimumIdle
     * 控制连接池空闲连接的最小数量，当连接池空闲连接少于minimumIdle，而且总共连接数不大于maximumPoolSize时，HikariCP会尽力补充新的连接。
     * 为了性能考虑，不建议设置此值，而是让HikariCP把连接池当做固定大小的处理，默认minimumIdle与maximumPoolSize一样。
     * 当minIdle<0或者minIdle>maxPoolSize,则被重置为maxPoolSize，该值默认为10。
     *
     * maximumPoolSize
     * 此属性控制允许池到达的最大大小，包括空闲和使用中的连接。基本上，此值将确定到数据库后端的实际连接的最大数量。
     * 对此的合理值最好由您的执行环境决定。
     * 当池达到此大小且没有空闲连接可用时，对getConnection（）的调用将connectionTimeout在超时前阻塞最多毫秒。
     * 请阅读有关游泳池尺寸的信息。 默认值：10
     */
    public static DataSource createDataSource(String url, String username, String password, String diverClassName) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(diverClassName);
        dataSource.setMinimumIdle(0);
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
