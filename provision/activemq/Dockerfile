FROM openjdk:11-jre-slim

COPY . /workdir
WORKDIR /workdir

ARG APP_NAME
ARG APP_VER

ENV APP_NAME=${APP_NAME:-activemq} \
    APP_VER=${APP_VER:-5.16.0}

RUN apt-get update \
    && apt-get install -y wget \
    && ./install-activemq.sh

WORKDIR /etc/$APP_NAME

EXPOSE 8161
EXPOSE 61616
CMD ["/bin/sh", "-c", "bin/activemq console"]
