package org.infodavid.commons.authentication.springboot.cfg;

import org.infodavid.commons.authentication.rest.configuration.AuthenticationSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * The Class SecurityConfiguration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends AuthenticationSecurityConfiguration {
    // noop
}
