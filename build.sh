#!/bin/bash

set -e

mvn clean package

IMAGE_NAME=weather-client

EXEC_NAME=weather.jar

mv ./target/*.jar $EXEC_NAME

docker build -t $IMAGE_NAME .

rm $EXEC_NAME