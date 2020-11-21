package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sproutsocial.nsq.Publisher;
import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NSQ;

@Log4j2
@Component
@Profile(COMM_NSQ)
@RequiredArgsConstructor
public class NsqAdapter implements CommAdapter {
    private final Publisher publisher;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Airplane airplane, Topic topic) {
        try {
            publisher.publish(topic.toString(), objectMapper.writeValueAsBytes(airplane));
        } catch (Exception e) {
            LOGGER.error("Could not publish message", e);
        }
    }
}
