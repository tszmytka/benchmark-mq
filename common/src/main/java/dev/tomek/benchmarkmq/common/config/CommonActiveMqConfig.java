package dev.tomek.benchmarkmq.common.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;

public abstract class CommonActiveMqConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://dockerhost:61616");
        connectionFactory.setUseAsyncSend(true);
        return new CachingConnectionFactory(connectionFactory);
    }
}
