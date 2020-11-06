package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.USE_SPRING_CLOUD_STREAM;

@Component
@Profile(USE_SPRING_CLOUD_STREAM)
@RequiredArgsConstructor
public class StreamBridgeAdapter implements CommAdapter {
//    private final StreamBridge streamBridge;

    @Override
    public void send(Airplane airplane, Topic topic) {
//        streamBridge.send("airplanesHandler-out-0", airplane);
    }
}
