package dev.tomek.benchmarkmq.common.config;

import dev.tomek.benchmarkmq.common.Topic;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;

public abstract class CommonPulsarConfig {
    protected static final String TOPIC_AIRPLANES = "non-persistent://public/default/" + Topic.AIRPLANES.toString();

    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient.builder().serviceUrl("pulsar://dockerhost:6650").build();
    }
}
