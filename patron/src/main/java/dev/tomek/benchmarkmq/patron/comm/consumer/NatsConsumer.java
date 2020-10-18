package dev.tomek.benchmarkmq.patron.comm.consumer;

import com.fasterxml.jackson.databind.ObjectReader;
import dev.tomek.benchmarkmq.patron.comm.RequestHandler;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;

@Log4j2
@Component
@Profile(COMM_NATS)
@RequiredArgsConstructor
public class NatsConsumer implements MessageHandler {
    private final RequestHandler requestHandler;
    private final ObjectReader requestReader;

    @Override
    public void onMessage(Message msg) throws InterruptedException {
        try {
            requestHandler.handleRequest(requestReader.readValue(msg.getData()));
        } catch (IOException e) {
            LOGGER.error("Could not deserialize", e);
        }
    }
}
