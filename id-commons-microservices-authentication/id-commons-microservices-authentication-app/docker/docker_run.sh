#!/bin/bash
if ! systemctl is-active --quiet docker.service;
then
	sudo systemctl restart docker.service
fi
DIR=~/workspace/java/commons-microservices/id-commons-microservices-authentication/id-commons-microservices-authentication-app/docker
SERVICE_HOME=$DIR
SERVICE_HOST=localhost
SERVICE_PORT=8000
export SERVICE_HOME
export SERVICE_PORT
mkdir $DIR/dumps >/dev/null 2>&1
sudo chmod a+rwX $DIR
sudo chmod -R a+rwX $DIR/dumps
DATE=`date '+%H%M%S'`
cd $DIR
docker-compose pull
docker-compose up -d --remove-orphans --force-recreate
#docker-compose up -d --remove-orphans
sleep 6
while ! nc -z ${SERVICE_HOST} 8888; do sleep 1; done
sleep 1
xdg-open "http://${SERVICE_HOST}:8888/" >/dev/null 2>&1
while ! nc -z ${SERVICE_HOST} 8761; do sleep 1; done
sleep 1
xdg-open "http://${SERVICE_HOST}:8761/" >/dev/null 2>&1
sleep 1
xdg-open "http://${SERVICE_HOST}:$SERVICE_PORT/" >/dev/null 2>&1
