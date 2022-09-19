package com.betha.services.outboxlib.databases.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager",
        basePackages = {"com.betha.services.outboxlib"})
public class PostgresDataSource {
    @Bean(name = "postgresDataSourceProperties")
    @ConfigurationProperties("spring.datasource-postgres")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "postgresDataSources")
    @ConfigurationProperties("spring.datasource-postgres.configuration")
    public DataSource postgresDataSource(@Qualifier("postgresDataSourceProperties") DataSourceProperties postgresDataSourceProperties) {
        return postgresDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            EntityManagerFactoryBuilder postgresEntityManagerFactoryBuilder, @Qualifier("postgresDataSources") DataSource postgresDataSource) {

        Map<String, String> postgresJpaProperties = new HashMap<>();
        postgresJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        postgresJpaProperties.put("hibernate.hbm2ddl.auto", "none");
        postgresJpaProperties.put("hibernate.ddl-auto", "none");
        postgresJpaProperties.put("show-sql", "true");

        return postgresEntityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("com.betha.pendenciasconsumer.model.postgres.outbox")
                .persistenceUnit("postgresDataSource")
                .properties(postgresJpaProperties)
                .build();
    }

    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager postgresTransactionManager(
            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory postgresEntityManagerFactory) {

        return new JpaTransactionManager(postgresEntityManagerFactory);
    }
}
