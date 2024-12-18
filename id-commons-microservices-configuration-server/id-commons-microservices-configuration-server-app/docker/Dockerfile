# Perform the extraction in a separate builder container
FROM amazoncorretto:21-alpine AS builder
WORKDIR /builder
ARG JAR_FILE=target/*.jar
ENV SERVICE_HOST=localhost
ENV SERVICE_PROFILE=production
ENV SERVICE_USER=root
ENV SERVICE_PASSWORD=secret
ENV SERVICE_DATABASE=test
ENV SERVICE_DATABASE_HOST=localhost
ENV SERVICE_DATABASE_PORT=5432
ENV SERVICE_DATABASE_USER=test
ENV SERVICE_DATABASE_PASSWORD=test
# Copy the jar file to the working directory and rename it to application.jar
COPY ${JAR_FILE} application.jar
# Extract the jar file using an efficient layout
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# This is the runtime container
FROM amazoncorretto:21-alpine
LABEL COMPANY="Infodavid"
LABEL MAINTAINER="contact@infodavid.org"
RUN apk add --no-cache curl
WORKDIR /application
# Copy the extracted jar contents from the builder container into the working directory in the runtime container
# Every copy step creates a new docker layer
# This allows docker to only pull the changes it really needs
COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./
COPY --from=builder /builder/extracted/application/ ./
# Copy the default configuration
COPY config/ ./config.orig/
RUN chmod -R a+rwX config.orig
# Copy the main script used to initialize and start the application
COPY docker-entrypoint.sh /usr/bin/
RUN chmod a+rx /usr/bin/docker-entrypoint.sh

VOLUME [ "/application/config", "/application/logs" ]
EXPOSE 8888

# Start the application jar - this is not the uber jar used by the builder
# This jar only contains application code and references to the extracted jar files
# This layout is efficient to start up and CDS friendly
ENTRYPOINT ["docker-entrypoint.sh"]
HEALTHCHECK --interval=25s --timeout=3s --retries=10 CMD curl -f http://localhost:8888/actuator/health || exit 1