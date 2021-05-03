package dev.tomek.benchmarkmq.patron.config;

import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.patron.comm.consumer.NatsConsumer;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Subscription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.*;

@Configuration
@Profile(COMM_NATS)
public class NatsConfig {
    @Bean
    public Dispatcher dispatcher(Connection connection) {
        return connection.createDispatcher(msg -> {});
    }

    @Profile("!" + USE_SPRING_CLOUD_STREAM)
    @Bean
    public Subscription subscriptionAirplanes(Dispatcher dispatcher, NatsConsumer natsConsumer, String topicPrefix) {
        return dispatcher.subscribe(topicPrefix + Topic.AIRPLANES, natsConsumer);
    }

    @Configuration
    @Profile("!" + NATS_PERSISTENCE)
    static class TopicConfigNoPersistence {
        @Bean
        public String topicPrefix() {
            return "";
        }
    }

    @Configuration
    @Profile(NATS_PERSISTENCE)
    static class TopicConfigPersistence {
        @Bean
        public String topicPrefix() {
            return "Persisted_";
        }
    }
}
