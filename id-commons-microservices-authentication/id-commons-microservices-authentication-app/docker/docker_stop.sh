#!/bin/bash
DIR=/tmp/id-commons-microservices
SERVICE_HOME=$DIR
export SERVICE_HOME
docker-compose -f ~/workspace/java/commons-microservices/id-commons-microservices-authentication/id-commons-microservices-authentication-app/docker/docker-compose.yaml down
if [ -d "$$DIR" ];
then
    sudo chmod -R a+rwX $DIR
fi