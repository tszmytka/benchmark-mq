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

### Latencies
| Transport Implementation                  | 10 msgs/s         | 100 msgs/s        | 100 msgs/s  |
| ----------------------------------------- | ----------------- | ----------------- | ----------- |
| ActiveMQ                                  | 4-5 ms         | 35 658 /s         |          |
