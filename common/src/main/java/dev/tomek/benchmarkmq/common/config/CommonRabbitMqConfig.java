package dev.tomek.benchmarkmq.common.config;

import dev.tomek.benchmarkmq.common.Topic;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public abstract class CommonRabbitMqConfig {
    @Bean
    public Queue airplanesQueue() {
        return new Queue(Topic.AIRPLANES.toString(), false);
    }
}
