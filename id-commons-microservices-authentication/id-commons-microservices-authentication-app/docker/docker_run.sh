#!/bin/bash
if ! systemctl is-active --quiet docker.service;
then
	sudo systemctl restart docker.service
fi
DIR=/tmp/id-commons-microservices
SERVICE_HOME=$DIR
SERVICE_HOST=localhost
SERVICE_PORT=8001
export SERVICE_HOME
export SERVICE_PORT
mkdir -p $DIR/dumps >/dev/null 2>&1
mkdir -p $DIR/config >/dev/null 2>&1
mkdir -p $DIR/logs >/dev/null 2>&1
sudo chmod a+rwX $DIR
sudo chmod -R a+rwX $DIR/dumps
sudo chmod -R a+rwX $DIR/config
sudo chmod -R a+rwX $DIR/logs
DATE=`date '+%H%M%S'`
cd ~/workspace/java/commons-microservices/id-commons-microservices-authentication/id-commons-microservices-authentication-app/docker
#docker-compose pull
docker-compose up -d --remove-orphans --force-recreate
#docker-compose up -d --remove-orphans
sleep 10
while ! nc -z ${SERVICE_HOST} 8888; do sleep 1; done
sleep 2
xdg-open "http://${SERVICE_HOST}:8888/" >/dev/null 2>&1
while ! nc -z ${SERVICE_HOST} 8761; do sleep 1; done
sleep 2
xdg-open "http://${SERVICE_HOST}:8761/" >/dev/null 2>&1
sleep 2
xdg-open "http://${SERVICE_HOST}:8080/" >/dev/null 2>&1
sleep 2
xdg-open "http://${SERVICE_HOST}:$SERVICE_PORT/" >/dev/null 2>&1
