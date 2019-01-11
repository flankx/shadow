package com.denachina.shadow.dbconfig;

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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresqlEntityManager",
        transactionManagerRef = "postgresqlTransactionManager",
        basePackages = "com.denachina.shadow.dao"
)
@PropertySource(value = "classpath:db.properties")
public class PostgresConfig {

    @Value("${postgresql.datasource.r.jdbc-url}")
    private String urlR;

    @Value("${postgresql.datasource.r.username}")
    private String userNameR;

    @Value("${postgresql.datasource.r.password}")
    private String passwordR;

    @Value("${postgresql.datasource.w.jdbc-url}")
    private String urlW;

    @Value("${postgresql.datasource.w.username}")
    private String userNameW;

    @Value("${postgresql.datasource.w.password}")
    private String passwordW;

    @Value("${postgresql.datasource.diverClassName}")
    private String diverClassName;

    /**
     * PostgreSQL datasource ReadOnly definition.
     */
    private DataSource postgresRDataSource() {
        return BaseConfig.createDataSource(urlR, userNameR, passwordR, diverClassName);
    }

    /**
     * PostgreSQL datasource Write definition.
     */
    private DataSource postgresWDataSource() {
        return BaseConfig.createDataSource(urlW, userNameW, passwordW, diverClassName);
    }

    @Bean
    public DataSource dynamicDataSource() {
        DataSource PostgresW = postgresWDataSource();
        DataSource PostgresR = postgresRDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(PostgresDBContextHolder.POSTGRES_R, PostgresR);
        targetDataSources.put(PostgresDBContextHolder.POSTGRES_W, PostgresW);

        PostgresDynamicDataSource dynamicDataSource = new PostgresDynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dynamicDataSource.setDefaultTargetDataSource(PostgresR);//配置默认的数据源

        return dynamicDataSource;
    }

    /**
     * Entity manager definition.
     */

    @Bean(name = "postgresqlEntityManager")
    public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dynamicDataSource())
                .packages("com.denachina.shadow.dao")
//                .properties(hibernateProperties())
                .persistenceUnit("database:postgres")
                .build();
    }

    @Bean(name = "postgresqlTransactionManager")
    public PlatformTransactionManager postgresqlTransactionManager(@Qualifier("postgresqlEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
/*
    private Map<String, Object> hibernateProperties() {

        Resource resource = new ClassPathResource("hibernate.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue())
                    );
        } catch (IOException e) {
            return new HashMap<>();
        }
    }*/

}
