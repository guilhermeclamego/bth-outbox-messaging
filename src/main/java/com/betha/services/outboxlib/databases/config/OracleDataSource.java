package com.betha.services.outboxlib.databases.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracleTransactionManager",
        basePackages = {"com.betha.services.outboxlib"})
public class OracleDataSource {
    @Primary
    @Bean(name = "oracleDataSourceProperties")
    @ConfigurationProperties("spring.datasource-oracle")
    public DataSourceProperties oracleDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "oracleDataSourceConfig")
    @ConfigurationProperties("spring.datasource-oracle.configuration")
    public DataSource oracleDataSource(@Qualifier("oracleDataSourceProperties") DataSourceProperties oracleDataSourceProperties) {
        return oracleDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "oracleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(
            EntityManagerFactoryBuilder oracleEntityManagerFactoryBuilder, @Qualifier("oracleDataSourceConfig") DataSource oracleDataSource) {

        Map<String, String> oracleJpaProperties = new HashMap<>();
        oracleJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        oracleJpaProperties.put("hibernate.hbm2ddl.auto", "none");
        oracleJpaProperties.put("hibernate.ddl-auto", "none");
        oracleJpaProperties.put("show-sql", "true");

        return oracleEntityManagerFactoryBuilder
                .dataSource(oracleDataSource)
                .packages("com.betha.services.outboxlib")
                .persistenceUnit("oracleDataSource")
                .properties(oracleJpaProperties)
                .build();
    }

    @Primary
    @Bean(name = "oracleTransactionManager")
    public PlatformTransactionManager oracleTransactionManager(
            @Qualifier("oracleEntityManagerFactory") EntityManagerFactory oracleEntityManagerFactory) {
        return new JpaTransactionManager(oracleEntityManagerFactory);
    }
}
