package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.Producer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_PULSAR;

@Log4j2
@Component
@Profile(COMM_PULSAR)
@RequiredArgsConstructor
public class PulsarAdapter implements CommAdapter {
    private final Map<Topic, Producer<byte[]>> msgProducers;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Airplane airplane, Topic topic) {
        try {
            msgProducers.get(topic).sendAsync(objectMapper.writeValueAsBytes(airplane));
        } catch (Exception e) {
            LOGGER.error("Could not send message", e);
        }
    }
}
