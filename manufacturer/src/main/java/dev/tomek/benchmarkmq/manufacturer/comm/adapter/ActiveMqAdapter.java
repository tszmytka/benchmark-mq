package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;

@Log4j2
@Component
@Profile(COMM_ACTIVEMQ)
@RequiredArgsConstructor
public class ActiveMqAdapter implements CommAdapter {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Airplane airplane, Topic topic) {
        try {
            jmsTemplate.convertAndSend(topic.toString(), objectMapper.writeValueAsBytes(airplane));
        } catch (Exception e) {
            LOGGER.error("Cannot send message", e);
        }
    }
}
