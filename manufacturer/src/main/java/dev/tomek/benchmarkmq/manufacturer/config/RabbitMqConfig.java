package dev.tomek.benchmarkmq.manufacturer.config;

import dev.tomek.benchmarkmq.common.config.CommonRabbitMqConfig;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_RABBITMQ;

@Profile(COMM_RABBITMQ)
@Configuration
public class RabbitMqConfig extends CommonRabbitMqConfig {
    // example manually creating connection
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("dockerhost", 5672);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
