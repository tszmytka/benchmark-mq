package dev.tomek.benchmarkmq.manufacturer.config;

import dev.tomek.benchmarkmq.common.config.CommonKafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_KAFKA;

@Configuration
@Profile(COMM_KAFKA)
public class KafkaConfig extends CommonKafkaConfig {
    @Bean
    public KafkaProducer<byte[], byte[]> kafkaProducer(String kafkaBootstrapServers) {
        return new KafkaProducer<>(
            Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers
            ),
            new ByteArraySerializer(),
            new ByteArraySerializer()
        );
    }
}
