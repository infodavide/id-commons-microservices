package org.infodavid.commons.microservices.configurationserver;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * The Class ConfigurationServerApplication.
 */
@EnableConfigServer
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ConfigurationServerApplication {

    /**
     * The main method.
     * @param args the arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(ConfigurationServerApplication.class);
        builder.bannerMode(Mode.OFF);
        builder.headless(true);
        builder.logStartupInfo(true);
        final SpringApplication application = builder.build();
        application.setAllowBeanDefinitionOverriding(true);
        application.run(args);
    }
}
