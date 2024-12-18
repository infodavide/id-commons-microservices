package org.infodavid.commons.microservices.gateway;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The Class GatewayApplication.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
public class GatewayApplication {

    /**
     * The main method.
     * @param args the arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(GatewayApplication.class);
        builder.bannerMode(Mode.OFF);
        builder.headless(true);
        builder.logStartupInfo(true);
        final SpringApplication application = builder.build();
        application.setAllowBeanDefinitionOverriding(true);
        application.run(args);
    }
}
