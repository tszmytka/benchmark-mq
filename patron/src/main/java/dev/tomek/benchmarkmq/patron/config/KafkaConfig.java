package dev.tomek.benchmarkmq.patron.config;

import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.common.config.CommonKafkaConfig;
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
public class KafkaConfig extends CommonKafkaConfig {
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

    @Bean
    public Topic currentTopic() {
        return Topic.AIRPLANES;
    }
}
