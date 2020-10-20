package dev.tomek.benchmarkmq.patron.config;

import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.patron.comm.consumer.RabbitMqConsumer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_RABBITMQ;

@Profile(COMM_RABBITMQ)
@Configuration
public class RabbitMqConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("dockerhost", 5672);
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory, RabbitMqConsumer rabbitMqConsumer) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Topic.AIRPLANES.toString());
        container.setMessageListener(rabbitMqConsumer);
        return container;
    }
}
