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

## Host machine
| CPU                               | CORES   | RAM        |
| --------------------------------- | ------  | ---------- |
| Inter Core i7-4720 @ 2.60GHz      | 4       | 8GB DDR3   |

## Results

### ActiveMQ
Based on `spring-boot-starter-activemq`

![ActiveMQ message performance chart](doc/img/active-mq.png "ActiveMQ message performance chart")

- Problems caused by re-scheduling message production in a separate thread from the beginning
- Using `AsyncSend` helps *a lot*
- Results weak nevertheless

### RabbitMQ
Based on `spring-rabbit`

![RabbitMQ message performance chart](doc/img/rabbit-mq.png "RabbitMQ message performance chart")

- Smooth and trouble-free cooperation
- Feels lightweight and simple

