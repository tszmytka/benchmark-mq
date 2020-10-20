FROM openjdk:11-jre-slim

COPY . /workdir
WORKDIR /workdir

ARG APP_NAME
ARG APP_VER
ARG SCALA_VER

ENV APP_NAME=${APP_NAME:-kafka} \
    APP_VER=${APP_VER:-2.5.0} \
    SCALA_VER=${SCALA_VER:-2.12}

RUN apt-get update \
    && apt-get install -y wget \
    && ./install-kafka.sh

WORKDIR /etc/$APP_NAME
ENTRYPOINT ["bin/kafka-server-start.sh", "config/server.properties"]
