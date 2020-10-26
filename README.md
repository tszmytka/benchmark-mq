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
| Version                               | Driver Library      |
| ------------------------------------- | ------------------  |
| Official image: rabbitmq:3.8-management-alpine | `spring-rabbit`       |

### ActiveMQ
| Version                               | Driver Library      |
| ------------------------------------- | ------------------  |
| Custom image: openjdk:11-jre-slim + ActiveMQ 5.16.0 | `spring-boot-starter-activemq`       |


![ActiveMQ message performance chart](doc/img/active-mq.png "ActiveMQ message performance chart")

- Problems caused by re-scheduling message production in a separate thread from the beginning
- Using `AsyncSend` helps *a lot*
- Weak results nevertheless, can't pass ~570 msg/s mark

### RabbitMQ
| Version                               | Driver Library      |
| ------------------------------------- | ------------------  |
| Official image: rabbitmq:3.8-management-alpine | `spring-rabbit`       |

![RabbitMQ message performance chart](doc/img/rabbit-mq.png "RabbitMQ message performance chart")

- Smooth and trouble-free cooperation till ~2 000 msg/s mark, multiple "hiccups" after that
- Feels lightweight and simple

### Pulsar
| Version                               | Driver Library      |
| ------------------------------------- | ------------------  |
| Official image: apachepulsar/pulsar:2.6.1 | `pulsar-client:2.6.1`       |

![Pulsar message performance chart](doc/img/pulsar.png "Pulsar message performance chart")

- Broker requires 2GB RAM to even boot up (!?!)
- Driver uses a baked-in, old version of Jackson - impossible to override it
- Consistently keeps latency in check

