#!/bin/sh
echo "entrypoint.sh called with environment: $@"
DEBUG=0
echo ""
echo "================================================================"
echo "JVM_XMX: $JVM_XMX"
echo "DEBUG: $DEBUG"
echo "SERVICE_HOST: $SERVICE_HOST"
echo "SERVICE_PORT: $SERVICE_PORT"
echo "SERVICE_PROFILE: $SERVICE_PROFILE"
echo "SERVICE_USER: $SERVICE_USER"
echo "SERVICE_PASSWORD: $SERVICE_PASSWORD"
echo "CONFIGURATION_SERVICE_HOST=$CONFIGURATION_SERVICE_HOST"
echo "CONFIGURATION_SERVICE_PORT=$CONFIGURATION_SERVICE_PORT"
echo "REGISTRY_SERVICE_HOST=$REGISTRY_SERVICE_HOST"
echo "REGISTRY_SERVICE_PORT=$REGISTRY_SERVICE_PORT"
echo "================================================================"
echo "Waiting for configuration server..."
while ! nc -z ${CONFIGURATION_SERVICE_HOST} ${CONFIGURATION_SERVICE_PORT}; do sleep 1; done
echo "================================================================"
echo "Waiting for registry server..."
while ! nc -z ${REGISTRY_SERVICE_HOST} ${REGISTRY_SERVICE_PORT}; do sleep 1; done
echo "================================================================"
echo "Starting application..."
cd /application
java -Dgateway_service_host="$SERVICE_HOST" \
    -Dgateway_service_port=$SERVICE_PORT \
    -Dgateway_service_profile=$SERVICE_PROFILE \
    -Dgateway_service_user=$SERVICE_USER \
    -Dgateway_service_password="$SERVICE_PASSWORD" \
    -Dconfiguration_server_host="$CONFIGURATION_SERVICE_HOST" \
    -Dconfiguration_server_port=$CONFIGURATION_SERVICE_PORT \
    -Dregistry_service_host="$REGISTRY_SERVICE_HOST" \
    -Dregistry_service_port=$REGISTRY_SERVICE_PORT \
    -jar application.jar