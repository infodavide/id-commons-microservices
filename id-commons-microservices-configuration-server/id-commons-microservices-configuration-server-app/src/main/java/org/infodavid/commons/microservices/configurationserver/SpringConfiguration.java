package org.infodavid.commons.microservices.configurationserver;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.infodavid.commons.persistence.jdbc.DatabaseConnectionDescriptor;
import org.infodavid.commons.persistence.jdbc.DatabaseConnector;
import org.infodavid.commons.persistence.jdbc.connector.PostgreSqlConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class SpringConfiguration.
 */
@Configuration
@ComponentScan(basePackages = {
        "org.infodavid"
})
@PropertySource("classpath:application.properties")
public class SpringConfiguration {

    /** The database host. */
    @Value("${database.host:localhost}")
    protected String databaseHost;

    /** The database name. */
    @Value("${database.name:test}")
    protected String databaseName;

    /** The database password. */
    @Value("${database.password:test}")
    protected String databasePassword;

    /** The database port. */
    @Value("${database.port:5432}")
    protected int databasePort;

    /** The database username. */
    @Value("${database.username:test}")
    protected String databaseUsername;

    /**
     * Data source.
     * @return the data source
     * @throws SQLException the SQL exception
     * @throws IOException  Signals that an I/O exception has occurred.
     */
    @Bean
    public DataSource dataSource() throws SQLException, IOException {
        final DatabaseConnectionDescriptor dcd = new DatabaseConnectionDescriptor();
        dcd.setDatabase(databaseName);
        dcd.setHostname(databaseHost);
        dcd.setPort(databasePort);
        dcd.setUser(databaseUsername);
        dcd.setPassword(databasePassword);
        final DatabaseConnector connector = new PostgreSqlConnector();

        return connector.buildDataSource(dcd);
    }
}
