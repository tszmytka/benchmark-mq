package dev.tomek.benchmarkmq.common.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;

public abstract class CommonActiveMqConfig {
    /**
     * Setting up connection manually
     */
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setUseAsyncSend(true);
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
    public ActiveMQConnectionFactoryCustomizer activeMQConnectionFactoryCustomizer() {
        return factory -> factory.setUseAsyncSend(true);
    }
}
