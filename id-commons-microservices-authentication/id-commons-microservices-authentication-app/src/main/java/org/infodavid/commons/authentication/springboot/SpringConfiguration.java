package org.infodavid.commons.authentication.springboot;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.infodavid.commons.authentication.persistence.dao.UserDao;
import org.infodavid.commons.authentication.persistence.jpa.AbstractAuthenticationJpaSpringConfiguration;
import org.infodavid.commons.authentication.service.UserService;
import org.infodavid.commons.authentication.service.impl.security.DefaultAuthenticationService;
import org.infodavid.commons.authentication.service.impl.security.DefaultAuthorizationService;
import org.infodavid.commons.authentication.service.impl.service.DefaultUserService;
import org.infodavid.commons.impl.service.DefaultConfigurationManager;
import org.infodavid.commons.impl.service.DefaultSchedulerService;
import org.infodavid.commons.impl.service.ValidationHelper;
import org.infodavid.commons.persistence.dao.ConfigurationPropertyDao;
import org.infodavid.commons.persistence.jdbc.DatabaseConnectionDescriptor;
import org.infodavid.commons.persistence.jdbc.DatabaseConnector;
import org.infodavid.commons.persistence.jdbc.connector.PostgreSqlConnector;
import org.infodavid.commons.rest.configuration.UniqueNameGenerator;
import org.infodavid.commons.service.SchedulerService;
import org.infodavid.commons.service.security.AuthenticationService;
import org.infodavid.commons.service.security.AuthorizationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

/**
 * The Class SpringConfiguration.
 */
@Configuration
@ComponentScan(basePackages = {
        "org.infodavid"
}, nameGenerator = UniqueNameGenerator.class)
@PropertySource("classpath:application.properties")
public class SpringConfiguration extends AbstractAuthenticationJpaSpringConfiguration {

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
     * Instantiates a new spring configuration.
     */
    public SpringConfiguration() {
        databaseDialectClassName = "org.hibernate.dialect.PostgreSQLDialect";
    }

    /**
     * Application configuration manager.
     * @param applicationContext the application context
     * @param validationHelper   the validation helper
     * @param dao                the data access object
     * @return the default configuration manager
     */
    @Bean("applicationConfigurationManager")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultConfigurationManager applicationConfigurationManager(final ApplicationContext applicationContext, final ValidationHelper validationHelper, final ConfigurationPropertyDao dao) {
        return new DefaultConfigurationManager(LoggerFactory.getLogger("ApplicationConfigurationManager"), applicationContext, validationHelper, dao, org.infodavid.commons.service.Constants.APPLICATION_SCOPE);
    }

    /**
     * Authentication configuration manager.
     * @param applicationContext the application context
     * @param validationHelper   the validation helper
     * @param dao                the data access object
     * @return the default configuration manager
     */
    @Bean("authenticationConfigurationManager")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultConfigurationManager authenticationConfigurationManager(final ApplicationContext applicationContext, final ValidationHelper validationHelper, final ConfigurationPropertyDao dao) {
        return new DefaultConfigurationManager(LoggerFactory.getLogger("AuthenticationConfigurationManager"), applicationContext, validationHelper, dao, org.infodavid.commons.authentication.service.Constants.AUTHENTICATION_SCOPE);
    }

    /**
     * Authentication service.
     * @param applicationContext   the application context
     * @param userDao              the user data access object
     * @param configurationManager the configuration manager
     * @return the factory bean
     * @throws Exception the exception
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public FactoryBean<AuthenticationService> authenticationService(final ApplicationContext applicationContext, final UserDao userDao, @Qualifier("authenticationConfigurationManager") final DefaultConfigurationManager configurationManager) throws Exception {
        return new FactoryBean<>() {

            /*
             * (non-Javadoc)
             * @see org.springframework.beans.factory.FactoryBean#getObject()
             */
            @Override
            public AuthenticationService getObject() throws Exception {
                return new DefaultAuthenticationService(LoggerFactory.getLogger(AuthenticationService.class), applicationContext, userDao, null, configurationManager);
            }

            @Override
            public Class<?> getObjectType() {
                return AuthenticationService.class;
            }
        };
    }

    /**
     * Authorization service.
     * @return the factory bean
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public FactoryBean<AuthorizationService> authorizationService() {
        return new FactoryBean<>() {

            /*
             * (non-Javadoc)
             * @see org.springframework.beans.factory.FactoryBean#getObject()
             */
            @Override
            public AuthorizationService getObject() throws Exception {
                return new DefaultAuthorizationService();
            }

            @Override
            public Class<?> getObjectType() {
                return AuthorizationService.class;
            }
        };
    }

    /*
     * (non-Javadoc)
     * @see org.infodavid.commons.persistence.jpa.AbstractJpaSpringConfiguration#dataSource()
     */
    @Override
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

    /**
     * Scheduler service.
     * @param applicationContext the application context
     * @return the scheduler service
     * @throws Exception the exception
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SchedulerService schedulerService(final ApplicationContext applicationContext) throws Exception {
        return new DefaultSchedulerService(LoggerFactory.getLogger(DefaultSchedulerService.class), applicationContext);
    }

    /**
     * User service.
     * @param applicationContext    the application context
     * @param validationHelper      the validation helper
     * @param dao                   the data access object
     * @param authenticationService the authentication service
     * @param authorizationService  the authorization service
     * @return the user service
     * @throws Exception the exception
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UserService userService(final ApplicationContext applicationContext, final ValidationHelper validationHelper, final UserDao dao, final AuthenticationService authenticationService, final AuthorizationService authorizationService) throws Exception {
        return new DefaultUserService(LoggerFactory.getLogger(UserService.class), applicationContext, validationHelper, dao, authenticationService, authorizationService);
    }
}
