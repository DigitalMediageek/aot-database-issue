package com.example.database.hikari;

import com.example.database.entity.graal1.MyData1;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypesScanner;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@EnableJpaRepositories(
        basePackages = { "com.example.database.entity.graal1" },
        entityManagerFactoryRef = "graal1EMF",
        transactionManagerRef = "graal1PTM"
)
@Configuration
public class Graal1DbConfig {

    @Bean("dataSource1")
    @Primary
    DataSource graal1DataSource(@Qualifier("graal1DbProps") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean("graal1DbProps")
    @ConfigurationProperties(prefix = "graal1.datasource")
    @Primary
    HikariConfig graal1DbProps() {
        return new HikariConfig();
    }

    @Bean("graal1EMF")
    @Primary
    LocalContainerEntityManagerFactoryBean graal1EMF(@Qualifier("dataSource1")DataSource dataSource,
                                                     @Qualifier("graal1EMFB") EntityManagerFactoryBuilder builder,
                                                     @Qualifier("graal1PMT")PersistenceManagedTypes persistenceManagedTypes) {
        return builder.dataSource(dataSource).packages(MyData1.class).managedTypes(persistenceManagedTypes).build();
    }


    @Bean("graal1JpaProps")
    @ConfigurationProperties(prefix = "graal1")
    JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean("graal1EMFB")
    @Primary
    EntityManagerFactoryBuilder graal1EMFB(ObjectProvider<PersistenceUnitManager> persistenceUnitManager,
                                           @Qualifier("graal1JpaProps") JpaProperties jpaProperties) {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties.get(), persistenceUnitManager.getIfAvailable());
    }

    @Bean("graal1PMT")
    @Primary
    PersistenceManagedTypes graal1PMT(ResourceLoader resourceLoader) {
        return new PersistenceManagedTypesScanner(resourceLoader)
                .scan("com.example.database.entity.graal1");
    }

    @Bean("graal1PTM")
    @Primary
    PlatformTransactionManager regularTransactionManager(
            @Qualifier("graal1EMF") LocalContainerEntityManagerFactoryBean regularEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(regularEntityManagerFactory.getObject()));
    }

}
