package com.example.database.hikari;

import com.example.database.entity.graal2.MyData2;
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
        basePackages = { "com.example.database.entity.graal2" },
        entityManagerFactoryRef = "graal2EMF",
        transactionManagerRef = "graal2PTM"
)
@Configuration
public class Graal2DbConfig {

    @Bean("dataSource2")
    @Primary
    DataSource graal2DataSource(@Qualifier("graal2DbProps") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean("graal2DbProps")
    @ConfigurationProperties(prefix = "graal2.datasource")
    @Primary
    HikariConfig graal2DbProps() {
        return new HikariConfig();
    }

    @Bean("graal2EMF")
    @Primary
    LocalContainerEntityManagerFactoryBean graal2EMF(@Qualifier("dataSource2")DataSource dataSource,
                                                     @Qualifier("graal2EMFB") EntityManagerFactoryBuilder builder,
                                                     @Qualifier("graal2PMT")PersistenceManagedTypes persistenceManagedTypes) {
        return builder.dataSource(dataSource).packages(MyData2.class).managedTypes(persistenceManagedTypes).build();
    }

    @Bean("graal2JpaProps")
    @ConfigurationProperties(prefix = "graal2")
    JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean("graal2EMFB")
    @Primary
    EntityManagerFactoryBuilder graal2EMFB(ObjectProvider<PersistenceUnitManager> persistenceUnitManager,
                                           @Qualifier("graal2JpaProps")JpaProperties jpaProperties) {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties.get(), persistenceUnitManager.getIfAvailable());
    }

    @Bean("graal2PMT")
    @Primary
    PersistenceManagedTypes graal2PMT(ResourceLoader resourceLoader) {
        return new PersistenceManagedTypesScanner(resourceLoader)
                .scan("com.example.database.entity.graal2");
    }

    @Bean("graal2PTM")
    @Primary
    PlatformTransactionManager regularTransactionManager(
            @Qualifier("graal2EMF") LocalContainerEntityManagerFactoryBean regularEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(regularEntityManagerFactory.getObject()));
    }

}
