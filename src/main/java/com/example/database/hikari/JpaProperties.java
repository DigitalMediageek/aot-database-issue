package com.example.database.hikari;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom JPA properties for datasource
 */
@Setter
public class JpaProperties {

    /**
     * Store jpa properties here
     */
    private Map<String, String> jpa;

    /**
     * Explicit getter for a nicer name
     * @return the properties
     */
    public Map<String, String> get() {
        return jpa;
    }
}
