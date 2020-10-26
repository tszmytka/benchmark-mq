package dev.tomek.benchmarkmq.patron.config;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.common.config.CommonPulsarConfig;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_PULSAR;

@Configuration
@Profile(COMM_PULSAR)
public class PulsarConfig extends CommonPulsarConfig {
    @Bean
    public Consumer<Airplane> msgConsumerRefinitiv(PulsarClient pulsarClient) throws PulsarClientException {
        return pulsarClient.newConsumer(JSONSchema.of(Airplane.class)).topic(TOPIC_AIRPLANES)
            .subscriptionName("subscription-" + Topic.AIRPLANES)
            .subscribe();
    }
}
