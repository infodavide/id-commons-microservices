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
echo "================================================================"
echo "Waiting for database..."
while ! nc -z ${SERVICE_DATABASE_HOST} ${SERVICE_DATABASE_PORT}; do sleep 1; done
echo "================================================================"
echo "Starting application..."
cd /application
java -Dconfiguration_server_host="$SERVICE_HOST" \
    -Dconfiguration_server_port=$SERVICE_PORT \
    -Dconfiguration_server_profile=$SERVICE_PROFILE \
    -Dconfiguration_server_user=$SERVICE_USER \
    -Dconfiguration_server_password="$SERVICE_PASSWORD" \
    -Ddatabase.name=$SERVICE_DATABASE \
    -Ddatabase.host="$SERVICE_DATABASE_HOST" \
    -Ddatabase.port=$SERVICE_DATABASE_PORT \
    -Ddatabase.username=$SERVICE_DATABASE_USER \
    -Ddatabase.password="$SERVICE_DATABASE_PASSWORD" \
    -jar application.jar