package dev.tomek.benchmarkmq.manufacturer.config;

import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.common.config.CommonPulsarConfig;
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
public class PulsarConfig extends CommonPulsarConfig {
    @Bean
    public Map<Topic, Producer<byte[]>> msgProducersCommand(PulsarClient pulsarClient) {
        return Arrays.stream(Topic.values())
            .collect(Collectors.toMap(Function.identity(), t -> {
                try {
                    return pulsarClient.newProducer(JSONSchema.BYTES).topic(TOPIC_AIRPLANES).create();
                } catch (PulsarClientException e) {
                    throw new RuntimeException(e);
                }
            }));
    }
}
