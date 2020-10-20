package dev.tomek.benchmarkmq.patron.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;

@Configuration
@Profile(COMM_ACTIVEMQ)
public class ActiveMqConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(new ActiveMQConnectionFactory("tcp://dockerhost:61616"));
    }
}
