package org.infodavid.commons.microservices.gateway;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * The Class GatewayApplication.
 */
@SpringBootApplication(scanBasePackages = "org.infodavid", exclude = {DataSourceAutoConfiguration.class })
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
