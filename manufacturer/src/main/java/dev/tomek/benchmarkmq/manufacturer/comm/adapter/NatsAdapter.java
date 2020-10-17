package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import io.nats.client.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;

@Log4j2
@Component
@Profile(COMM_NATS)
@RequiredArgsConstructor
public class NatsAdapter implements CommAdapter {
    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Airplane airplane, Topic topic) {
        try {
            connection.publish(topic.toString(), objectMapper.writeValueAsBytes(airplane));
        } catch (JsonProcessingException e) {
            LOGGER.error("Could not serialize", e);
        }

    }
}
