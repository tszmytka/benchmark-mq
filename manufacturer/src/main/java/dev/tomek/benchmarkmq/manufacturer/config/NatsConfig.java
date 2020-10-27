package dev.tomek.benchmarkmq.manufacturer.config;

import dev.tomek.benchmarkmq.common.config.CommonNatsConfig;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;

@Configuration
@Profile(COMM_NATS)
public class NatsConfig extends CommonNatsConfig {
}
