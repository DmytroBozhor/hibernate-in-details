package org.example;

import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLContainerRunner {

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.3");

    static {
        POSTGRESQL_CONTAINER.start();
    }

    public static SessionFactory buildSessionFactory() {
        var configuration = HibernateUtil.getConfiguration();
        configuration.setProperty("hibernate.connection.url", POSTGRESQL_CONTAINER.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", POSTGRESQL_CONTAINER.getUsername());
        configuration.setProperty("hibernate.connection.password", POSTGRESQL_CONTAINER.getPassword());

        return configuration.buildSessionFactory();
    }

}
