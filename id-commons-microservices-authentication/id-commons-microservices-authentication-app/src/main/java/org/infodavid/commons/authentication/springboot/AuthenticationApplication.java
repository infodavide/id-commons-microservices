package org.infodavid.commons.authentication.springboot;

import org.infodavid.commons.springboot.AbstractApplication;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * The Class AuthenticationApplication.
 */
@SpringBootApplication(scanBasePackages = "org.infodavid", exclude = {
        SecurityAutoConfiguration.class
})
public class AuthenticationApplication extends AbstractApplication {

    /**
     * The main method.
     * @param args the arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(AuthenticationApplication.class);
        builder.bannerMode(Mode.OFF);
        builder.headless(true);
        builder.logStartupInfo(true);
        final SpringApplication application = builder.build();
        application.setAllowBeanDefinitionOverriding(true);
        application.run(args);
    }
}
