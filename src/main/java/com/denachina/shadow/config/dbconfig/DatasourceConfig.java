package com.denachina.shadow.config.dbconfig;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "customEntityManagerFactory",
    transactionManagerRef = "customTransactionManager", basePackages = "com.denachina.shadow.dao")
@PropertySource(value = "classpath:db.properties")
public class DatasourceConfig {

    @Value("${mysql.datasource.r.jdbc-url}")
    private String urlR;

    @Value("${mysql.datasource.r.username}")
    private String userNameR;

    @Value("${mysql.datasource.r.password}")
    private String passwordR;

    @Value("${mysql.datasource.w.jdbc-url}")
    private String urlW;

    @Value("${mysql.datasource.w.username}")
    private String userNameW;

    @Value("${mysql.datasource.w.password}")
    private String passwordW;

    @Value("${mysql.datasource.diverClassName}")
    private String diverClassName;

    /**
     * datasource ReadOnly definition.
     */
    private DataSource datasourceR() {
        return BaseConfig.createDataSource(urlR, userNameR, passwordR, diverClassName);
    }

    /**
     * datasource Write definition.
     */
    private DataSource datasourceW() {
        return BaseConfig.createDataSource(urlW, userNameW, passwordW, diverClassName);
    }

    @Bean
    public DataSource dynamicDataSource() {
        DataSource write = datasourceW();
        DataSource read = datasourceR();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBContextHolder.R, read);
        targetDataSources.put(DBContextHolder.W, write);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 配置默认的数据源
        dynamicDataSource.setDefaultTargetDataSource(read);

        return dynamicDataSource;
    }

    /**
     * Entity manager definition.
     */
    @Bean(name = "customEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dynamicDataSource()).packages("com.denachina.shadow.dao")
            // .properties(hibernateProperties())
            .persistenceUnit("database:mysql").build();
    }

    @Bean(name = "customTransactionManager")
    public PlatformTransactionManager
        customTransactionManager(@Qualifier("customEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
