package dev.tomek.benchmarkmq.patron.config;

import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.patron.comm.consumer.NatsConsumer;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.nats.client.Subscription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;

@Configuration
@Profile(COMM_NATS)
public class NatsConfig {
    @Bean
    public Connection connection() throws IOException, InterruptedException {
        return Nats.connect("nats://dockerhost:4222");
    }

    @Bean
    public Dispatcher dispatcher(Connection connection) {
        return connection.createDispatcher(msg -> {});
    }

    @Bean
    public Subscription subscriptionRefinitiv(Dispatcher dispatcher, NatsConsumer natsConsumer) {
        return dispatcher.subscribe(Topic.AIRPLANES.toString(), natsConsumer);
    }
}
