package org.infodavid.commons.authentication.springboot;

import org.infodavid.commons.springboot.AbstractApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * The Class AuthenticationApplication.
 */
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
public class AuthenticationApplication extends AbstractApplication {

    protected static SpringApplication application;

    /**
     * The main method.
     * @param args the arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        application = main(AuthenticationApplication.class, args);
        application.run(args);
    }
}
