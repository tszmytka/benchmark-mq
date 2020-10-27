package dev.tomek.benchmarkmq.common.config;

import org.springframework.context.annotation.Bean;

public class CommonKafkaConfig {
    @Bean
    public String kafkaBootstrapServers() {
        return "kafka:9092";
    }
}
