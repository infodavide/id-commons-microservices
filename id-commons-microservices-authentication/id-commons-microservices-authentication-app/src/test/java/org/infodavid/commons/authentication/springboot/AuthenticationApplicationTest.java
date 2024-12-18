package org.infodavid.commons.authentication.springboot;

import org.infodavid.commons.persistence.jdbc.connector.PostgreSqlConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * The Class AuthenticationApplicationTest.
 */
@SpringBootTest(classes = DevApplication.class)
class AuthenticationApplicationTest {

    /** The postgres container. */
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.6");

    /**
     * Sets the up class.
     */
    @BeforeAll
    public static void setUpClass() {
        container.start();
        System.setProperty("database.host", container.getHost());
        System.setProperty("database.port", container.getMappedPort(PostgreSqlConnector.DEFAULT_PORT).toString());
        System.setProperty("database.name", container.getDatabaseName());
        System.setProperty("database.username", container.getUsername());
        System.setProperty("database.password", container.getPassword());
    }

    /**
     * Tear down class.
     */
    @AfterAll
    public static void tearDownClass() {
        container.stop();
    }

    /**
     * Context loads.
     */
    @Test
    void contextLoads() {
        // noop
    }
}
