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
echo "SERVICE_DATABASE=$SERVICE_DATABASE"
echo "SERVICE_DATABASE_HOST=$SERVICE_DATABASE_HOST"
echo "SERVICE_DATABASE_PORT=$SERVICE_DATABASE_PORT"
echo "SERVICE_DATABASE_USER=$SERVICE_DATABASE_USER"
echo "SERVICE_DATABASE_PASSWORD=$SERVICE_DATABASE_PASSWORD"
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
echo "Waiting for database..."
while ! nc -z ${SERVICE_DATABASE_HOST} ${SERVICE_DATABASE_PORT}; do sleep 1; done
echo "================================================================"
echo "Starting application..."
cd /application
java -Dauthentication_service_host="$SERVICE_HOST" \
    -Dauthentication_service_port=$SERVICE_PORT \
    -Dauthentication_service_profile=$SERVICE_PROFILE \
    -Dauthentication_service_user=$SERVICE_USER \
    -Dauthentication_service_password="$SERVICE_PASSWORD" \
    -Ddatabase.name=$SERVICE_DATABASE \
    -Ddatabase.host="$SERVICE_DATABASE_HOST" \
    -Ddatabase.port=$SERVICE_DATABASE_PORT \
    -Ddatabase.username=$SERVICE_DATABASE_USER \
    -Ddatabase.password="$SERVICE_DATABASE_PASSWORD" \
    -Dconfiguration_server_host="$CONFIGURATION_SERVICE_HOST" \
    -Dconfiguration_server_port=$CONFIGURATION_SERVICE_PORT \
    -Dregistry_service_host="$REGISTRY_SERVICE_HOST" \
    -Dregistry_service_port=$REGISTRY_SERVICE_PORT \
    -cp "/application/lib:/application/conf" \
    -jar application.jar