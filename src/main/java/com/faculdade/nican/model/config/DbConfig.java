package com.faculdade.nican.model.config;

import java.util.HashMap;
import java.util.Map;

public final class DbConfig {

    private DbConfig() {}

    public static String host() {
        return value("DB_HOST", "localhost");
    }

    public static String port() {
        return value("DB_PORT", "5432");
    }

    public static String database() {
        return value("DB_NAME", "nicandb");
    }

    public static String user() {
        return value("DB_USER", "postgres");
    }

    public static String password() {
        return value("DB_PASS", "postgres");
    }

    public static String jdbcUrl() {
        return "jdbc:postgresql://" + host() + ":" + port() + "/" + database();
    }

    public static Map<String, String> jpaProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.url", jdbcUrl());
        props.put("jakarta.persistence.jdbc.user", user());
        props.put("jakarta.persistence.jdbc.password", password());
        props.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        return props;
    }

    private static String value(String name, String defaultValue) {
        String env = System.getenv(name);
        if (env != null && !env.isBlank()) {
            return env.trim();
        }

        String prop = System.getProperty(name);
        if (prop != null && !prop.isBlank()) {
            return prop.trim();
        }

        return defaultValue;
    }
}
