package dev.tomek.benchmarkmq.patron.config;

import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.common.config.CommonNatsConfig;
import dev.tomek.benchmarkmq.patron.comm.consumer.NatsConsumer;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Subscription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;
import static dev.tomek.benchmarkmq.common.Profiles.USE_SPRING_CLOUD_STREAM;

@Configuration
@Profile(COMM_NATS)
public class NatsConfig extends CommonNatsConfig {
    @Bean
    public Dispatcher dispatcher(Connection connection) {
        return connection.createDispatcher(msg -> {});
    }

    @Profile("!" + USE_SPRING_CLOUD_STREAM)
    @Bean
    public Subscription subscriptionAirplanes(Dispatcher dispatcher, NatsConsumer natsConsumer) {
        return dispatcher.subscribe(Topic.AIRPLANES.toString(), natsConsumer);
    }
}
