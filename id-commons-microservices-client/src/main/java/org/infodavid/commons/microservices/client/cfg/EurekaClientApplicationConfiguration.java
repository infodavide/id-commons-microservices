package org.infodavid.commons.microservices.client.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.netflix.discovery.shared.transport.jersey3.Jersey3TransportClientFactories;

/**
 * The Class EurekaClientApplicationConfiguration.
 */
@Configuration
@ComponentScan(basePackages = {
        "org.infodavid"
})
@PropertySource("classpath:application.properties")
public class EurekaClientApplicationConfiguration {

    /**
     * Jersey 3 transport client factories.
     * @return the jersey 3 transport client factories
     */
    @Bean
    public Jersey3TransportClientFactories jersey3TransportClientFactories() {
        return new Jersey3TransportClientFactories();
    }
}
