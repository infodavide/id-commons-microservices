package org.infodavid.commons.microservices.configurationserver;

import java.util.HashMap;
import java.util.Map;

import org.infodavid.commons.test.docker.DockerContainer;

import com.github.dockerjava.api.model.ExposedPort;

/**
 * The Class ConfigurationServerApplicationDev.
 */
public class ConfigurationServerApplicationDev {

    /**
     * The main method.
     * @param args the arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        final Map<String, String> env = new HashMap<>();
        env.put("POSTGRES_DB", "test");
        env.put("POSTGRES_USER", "test");
        env.put("POSTGRES_PASSWORD", "test");
        DockerContainer container = null;

        try {
            container = new DockerContainer("postgres:16.6", "postgres", env, ExposedPort.tcp(5432));
            container.start(30000);
            System.setProperty("database.host", "localhost");
            System.setProperty("database.port", String.valueOf(container.getPorts().get(0).getPort()));
            System.setProperty("database.name", container.getEnv().get("POSTGRES_DB"));
            System.setProperty("database.username", container.getEnv().get("POSTGRES_USER"));
            System.setProperty("database.password", container.getEnv().get("POSTGRES_PASSWORD"));
            ConfigurationServerApplication.main(args);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
