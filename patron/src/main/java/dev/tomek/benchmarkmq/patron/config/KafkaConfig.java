package dev.tomek.benchmarkmq.patron.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_KAFKA;

@Configuration
@Profile(COMM_KAFKA)
public class KafkaConfig {
    @Bean
    public String kafkaBootstrapServers() {
        return "kafka:9092";
    }

    @Bean
    public KafkaConsumer<byte[], byte[]> kafkaConsumer(String kafkaBootstrapServers) {
        return new KafkaConsumer<>(
            Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG, "patron"
            ),
            new ByteArrayDeserializer(),
            new ByteArrayDeserializer()
        );
    }
}
