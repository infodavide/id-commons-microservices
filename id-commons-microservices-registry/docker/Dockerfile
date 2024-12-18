# Perform the extraction in a separate builder container
FROM amazoncorretto:21-alpine AS builder
WORKDIR /builder
ARG JAR_FILE=target/*.jar
ENV SERVICE_HOST=localhost
ENV SERVICE_PORT=8761
ENV SERVICE_PROFILE=production
ENV SERVICE_USER=root
ENV SERVICE_PASSWORD=secret
ENV CONFIGURATION_SERVICE_HOST=localhost
ENV CONFIGURATION_SERVICE_PORT=8888

# Copy the jar file to the working directory and rename it to application.jar
COPY ${JAR_FILE} application.jar
# Extract the jar file using an efficient layout
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# This is the runtime container
FROM amazoncorretto:21-alpine
LABEL COMPANY="Infodavid"
LABEL MAINTAINER="contact@infodavid.org"
WORKDIR /application
# Copy the extracted jar contents from the builder container into the working directory in the runtime container
# Every copy step creates a new docker layer
# This allows docker to only pull the changes it really needs
COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./
# Copy the main script used to initialize and start the application
COPY docker-entrypoint.sh /usr/bin/
RUN chmod a+rx /usr/bin/docker-entrypoint.sh
# Start the application jar - this is not the uber jar used by the builder
# This jar only contains application code and references to the extracted jar files
# This layout is efficient to start up and CDS friendly
ENTRYPOINT ["docker-entrypoint.sh"]