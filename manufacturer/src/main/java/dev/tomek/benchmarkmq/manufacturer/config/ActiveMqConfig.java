package dev.tomek.benchmarkmq.manufacturer.config;

import dev.tomek.benchmarkmq.common.config.CommonActiveMqConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;

@Configuration
@Profile(COMM_ACTIVEMQ)
public class ActiveMqConfig extends CommonActiveMqConfig {
    @Bean
    public JmsTemplate jmsTemplate(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDeliveryPersistent(false);
        return jmsTemplate;
    }
}
