# benchmark-mq
A benchmark application comparing message queue implementations
* ActiveMQ
* RabbitMQ
* Pulsar
* Nats
* Redis
* Kafka

## Technology stack
* jdk15
* gradle-6.7
* spring boot 2.4
* lombok 1.18
* jackson 2.12


## Results

### ActiveMQ

![ActiveMQ message performance chart](doc/img/active-mq.png "ActiveMQ message performance chart")

- Problems caused re-scheduling message production in a separate thread from the beginning
- Using `AsyncSend` helps *a lot*
- Results weak nevertheless
