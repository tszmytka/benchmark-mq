package dev.tomek.benchmarkmq.common.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;

public abstract class CommonActiveMqConfig {
    // setting up connection manually
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://dockerhost:61616");
        connectionFactory.setUseAsyncSend(true);
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public ActiveMQConnectionFactoryCustomizer activeMQConnectionFactoryCustomizer() {
        return factory -> factory.setUseAsyncSend(true);
    }
}
