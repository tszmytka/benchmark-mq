package dev.tomek.benchmarkmq.manufacturer.config;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_PULSAR;

@Configuration
@Profile(COMM_PULSAR)
public class PulsarConfig {
    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient.builder().serviceUrl("pulsar://dockerhost:6650").build();
    }

    @Bean
    public Map<Topic, Producer<Airplane>> msgProducersCommand(PulsarClient pulsarClient) {
        return Arrays.stream(Topic.values())
            .collect(Collectors.toMap(Function.identity(), t -> {
                try {
                    final String topicName = "non-persistent://public/default/" + t.toString();
                    return pulsarClient.newProducer(JSONSchema.of(Airplane.class)).topic(topicName).create();
                } catch (PulsarClientException e) {
                    throw new RuntimeException(e);
                }
            }));
    }
}
