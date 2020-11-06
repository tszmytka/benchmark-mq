package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import io.nats.client.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_KAFKA;
import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;

@Log4j2
@Component
// nocommit
@Profile(COMM_KAFKA)
@RequiredArgsConstructor
public class NatsAdapter implements CommAdapter {
    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Airplane airplane, Topic topic) {
        try {
            connection.publish(topic.toString(), objectMapper.writeValueAsBytes(airplane));
        } catch (Exception e) {
            LOGGER.error("Could not send message", e);
        }
    }
}
