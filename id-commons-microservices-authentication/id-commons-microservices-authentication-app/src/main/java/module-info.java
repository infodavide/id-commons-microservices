module org.infodavid.commons.authentication.springboot {
    exports org.infodavid.commons.authentication.springboot.cfg;
    exports org.infodavid.commons.authentication.springboot;

    opens org.infodavid.commons.authentication.springboot.cfg;
    opens org.infodavid.commons.authentication.springboot;

    requires transitive org.infodavid.commons.authentication.rest;
    requires transitive org.infodavid.commons.service.impl;
    requires transitive org.infodavid.commons.persistence.jdbc;
    requires transitive org.infodavid.commons.authentication.persistence;
    requires transitive org.infodavid.commons.authentication.persistence.jpa;
    requires transitive org.infodavid.commons.authentication.service.impl;
    requires transitive org.infodavid.commons.rest;
    requires transitive org.infodavid.commons.springboot;
    requires transitive spring.beans;
    requires transitive spring.boot.autoconfigure;
    requires transitive spring.context;
    requires transitive spring.core;
    requires transitive spring.security.config;
    requires transitive jakarta.annotation;
    requires lombok;
}