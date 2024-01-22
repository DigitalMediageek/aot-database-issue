package com.example.database.hikari;

import com.example.database.entity.MyData;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypesScanner;

import javax.sql.DataSource;

/*
@EnableJpaRepositories(
        entityManagerFactoryRef = "graalEMF",
        basePackages = { "com.example.database.entity" }
)
@Configuration
 */
public class GraalDbConfig {
/*
    @Autowired
    private Environment environment;

    @Bean("dataSource")
    @Primary
    DataSource graalDataSource(@Qualifier("graalDbProps") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean("graalDbProps")
    @ConfigurationProperties(prefix = "graal.datasource")
    @Primary
    HikariConfig graalDbProps() {
        return new HikariConfig();
    }

    @Bean("graalEMF")
    @Primary
    LocalContainerEntityManagerFactoryBean graalEMF(@Qualifier("dataSource")DataSource dataSource,
                                                    EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages(MyData.class).build();
    }

    @Bean("graalPMT")
    @Primary
    PersistenceManagedTypes graalPMT(ResourceLoader resourceLoader) {
        return new PersistenceManagedTypesScanner(resourceLoader)
                .scan("com.example.database.entity");
    }

 */
}
