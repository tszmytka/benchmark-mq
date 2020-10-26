package dev.tomek.benchmarkmq.patron.config;

import dev.tomek.benchmarkmq.common.config.CommonActiveMqConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;

@Configuration
@Profile(COMM_ACTIVEMQ)
public class ActiveMqConfig extends CommonActiveMqConfig {
}
