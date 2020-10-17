package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Profiles;
import dev.tomek.benchmarkmq.common.Topic;
import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.Producer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_PULSAR;

@Component
@Profile(COMM_PULSAR)
@RequiredArgsConstructor
public class PulsarAdapter implements CommAdapter {
    private final Map<Topic, Producer<Airplane>> msgProducers;

    @Override
    public void send(Airplane airplane, Topic topic) {
        // todo serialize
        msgProducers.get(topic).sendAsync(airplane);
    }
}
