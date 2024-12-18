package org.infodavid.commons.authentication.springboot;

import org.infodavid.commons.persistence.jdbc.connector.PostgreSqlConnector;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * The Class DevApplication.
 */
public class DevApplication extends AuthenticationApplication {

    /** The postgres container. */
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.6");

    /**
     * The main method.
     * @param args the arguments
     */

    public static void main(final String[] args) {
        container.start();
        System.setProperty("database.host", container.getHost());
        System.setProperty("database.port", container.getMappedPort(PostgreSqlConnector.DEFAULT_PORT).toString());
        System.setProperty("database.name", container.getDatabaseName());
        System.setProperty("database.username", container.getUsername());
        System.setProperty("database.password", container.getPassword());

        try {
            AuthenticationApplication.main(args);
        } finally {
            System.out.println("Context stopped");
            container.stop();
        }
    }
}
