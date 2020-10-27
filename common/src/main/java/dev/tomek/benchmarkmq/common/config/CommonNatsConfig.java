package dev.tomek.benchmarkmq.common.config;

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public class CommonNatsConfig {
    @Bean
    public Connection connection() throws IOException, InterruptedException {
        return Nats.connect("nats://dockerhost:4222");
    }
}
