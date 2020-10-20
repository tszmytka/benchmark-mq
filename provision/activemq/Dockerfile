FROM openjdk:8-jre-alpine

ENV ACTIVEMQ_VERSION 5.16.0
ENV ACTIVEMQ_WORKDIR /app

RUN mkdir -p $ACTIVEMQ_WORKDIR

COPY activemq/apache-activemq-5.15.11-bin.tar.gz .

RUN tar -xzf apache-activemq-5.15.11-bin.tar.gz -C $ACTIVEMQ_WORKDIR

RUN ln -s $ACTIVEMQ_WORKDIR/apache-activemq-$ACTIVEMQ_VERSION \$ACTIVEMQ_WORKDIR/activemq

COPY conf/activemq.xml $ACTIVEMQ_WORKDIR/activemq/conf

COPY conf/db.properties $ACTIVEMQ_WORKDIR/activemq/conf/db.properties

COPY lib $ACTIVEMQ_WORKDIR/activemq/lib

WORKDIR $ACTIVEMQ_WORKDIR/activemq
EXPOSE 8161
EXPOSE 61616
CMD ["/bin/sh", "-c", "bin/activemq console"]
