package dev.tomek.benchmarkmq.manufacturer.config;

import com.sproutsocial.nsq.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NSQ;

@Configuration
@Profile(COMM_NSQ)
public class NsqConfig {
    @Bean
    public Publisher publisher() {
        return new Publisher("localhost");
    }
}
