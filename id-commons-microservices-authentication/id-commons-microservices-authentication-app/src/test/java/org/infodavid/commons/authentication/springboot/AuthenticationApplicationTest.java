package org.infodavid.commons.authentication.springboot;

import java.util.HashMap;
import java.util.Map;

import org.infodavid.commons.persistence.jdbc.connector.PostgreSqlConnector;
import org.infodavid.commons.test.docker.DockerContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dockerjava.api.model.ExposedPort;

/**
 * The Class AuthenticationApplicationTest.
 */
@SpringBootTest(classes = AuthenticationApplicationDev.class)
class AuthenticationApplicationTest {

    /** The container. */
    static DockerContainer container;

    static {
        final Map<String, String> env = new HashMap<>();
        env.put("POSTGRES_DB","test");
        env.put("POSTGRES_USER","test");
        env.put("POSTGRES_PASSWORD","test");
        container = new DockerContainer("postgres:16.6", "postgres", env, ExposedPort.tcp(PostgreSqlConnector.DEFAULT_PORT));
    }

    /**
     * Sets the up class.
     */
    @BeforeAll
    public static void setUpClass() {
        container.start(30000);
        System.setProperty("database.host", "localhost");
        System.setProperty("database.port", String.valueOf(container.getPorts().get(0).getPort()));
        System.setProperty("database.name", container.getEnv().get("POSTGRES_DB"));
        System.setProperty("database.username", container.getEnv().get("POSTGRES_USER"));
        System.setProperty("database.password", container.getEnv().get("POSTGRES_PASSWORD"));
    }

    /**
     * Tear down class.
     */
    @AfterAll
    public static void tearDownClass() {
        container.stop();
        container.delete();
    }

    /**
     * Context loads.
     */
    @Test
    void testContextLoads() { //NOSONAR No assertion
        // noop
    }
}
