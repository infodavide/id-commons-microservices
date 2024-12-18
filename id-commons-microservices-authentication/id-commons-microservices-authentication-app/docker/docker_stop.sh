#!/bin/bash
DIR=~/workspace/java/commons-microservices/id-commons-microservices-authentication/id-commons-microservices-authentication-app/docker
SERVICE_HOME=$DIR
export SERVICE_HOME
docker-compose -f "$DIR/docker-compose.yaml" down
sudo chmod -R a+rwX $DIR