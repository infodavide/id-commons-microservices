#!/bin/sh
echo "entrypoint.sh called with environment: $@"
DEBUG=0
SERVICE_PORT=8761
SERVICE_HOME=/application
JAVA_OPTS=
echo ""
echo "================================================================"
echo "DEBUG: $DEBUG"
echo "SERVICE_HOST: $SERVICE_HOST"
echo "SERVICE_PORT: $SERVICE_PORT"
echo "SERVICE_PROFILE: $SERVICE_PROFILE"
echo "SERVICE_USER: $SERVICE_USER"
echo "SERVICE_PASSWORD: $SERVICE_PASSWORD"
echo "CONFIGURATION_SERVICE_HOST=$CONFIGURATION_SERVICE_HOST"
echo "CONFIGURATION_SERVICE_PORT=$CONFIGURATION_SERVICE_PORT"
if [ ! -z "$JVM_XMX"];
then
    JAVA_OPTS="$JAVA_OPTS -Xmx${JVM_XMX}m"
fi
JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=9991"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.authenticate=false"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.ssl=false"
JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.jndi.ldap.object.disableEndpointIdentification=true"
JAVA_OPTS="$JAVA_OPTS -Djsse.enableSNIExtension=false"
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC"
JAVA_OPTS="$JAVA_OPTS -XX:+UseStringDeduplication"
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError"
JAVA_OPTS="$JAVA_OPTS -XX:HeapDumpPath=$SERVICE_HOME/logs/heapdumps"
JAVA_OPTS="$JAVA_OPTS --add-opens=java.base/java.lang=ALL-UNNAMED"
JAVA_OPTS="$JAVA_OPTS --add-opens=java.base/java.io=ALL-UNNAMED"
JAVA_OPTS="$JAVA_OPTS --add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED"
if [ -e "$SERVICE_HOME/conf/setenv.sh" ]; then
    . "$SERVICE_HOME/conf/setenv.sh"
fi
JAVA_OPTS="$JAVA_OPTS"
echo "JAVA_OPTS=$JAVA_OPTS"
echo "================================================================"
echo "Waiting for configuration server..."
while ! nc -z ${CONFIGURATION_SERVICE_HOST} ${CONFIGURATION_SERVICE_PORT}; do sleep 1; done
echo "================================================================"
if [ ! -d "$SERVICE_HOME/config" ];
then
    mkdir -p "$SERVICE_HOME/config"
fi
if [ "$(ls -A $SERVICE_HOME/config | wc -l)" -eq 0 ];
then
    echo "Copying config..."
    cp -R $SERVICE_HOME/config.orig/* $SERVICE_HOME/config/
fi
echo "Starting application..."
cd $SERVICE_HOME
java -Dregistry_service.host="$SERVICE_HOST" \
    -Dregistry_service.port=$SERVICE_PORT \
    -Dregistry_service.profile=$SERVICE_PROFILE \
    -Dregistry_service.user=$SERVICE_USER \
    -Dregistry_service.password="$SERVICE_PASSWORD" \
    -Dconfiguration_server.host="$CONFIGURATION_SERVICE_HOST" \
    -Dconfiguration_server.port=$CONFIGURATION_SERVICE_PORT \
    -cp "$SERVICE_HOME/config" \
    -Dlogging.config=$SERVICE_HOME/config/logback.xml \
    $JAVA_OPTS \
    -jar application.jar