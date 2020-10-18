package dev.tomek.benchmarkmq.patron.comm.consumer;

import com.fasterxml.jackson.databind.ObjectReader;
import dev.tomek.benchmarkmq.patron.comm.RequestHandler;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;

@Log4j2
@Component
@Profile(COMM_NATS)
@RequiredArgsConstructor
public class NatsConsumer implements MessageHandler {
    private final RequestHandler requestHandler;
    private final ObjectReader airplaneReader;

    @Override
    public void onMessage(Message msg) throws InterruptedException {
        try {
            requestHandler.handleRequest(airplaneReader.readValue(msg.getData()));
        } catch (Exception e) {
            LOGGER.error("Could not handle message", e);
        }
    }
}
