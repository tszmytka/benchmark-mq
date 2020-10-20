package dev.tomek.benchmarkmq.patron.comm.consumer;

import com.fasterxml.jackson.databind.ObjectReader;
import dev.tomek.benchmarkmq.patron.comm.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;

@Log4j2
@Component
@Profile(COMM_ACTIVEMQ)
@RequiredArgsConstructor
public class ActiveMqConsumer {
    private final ObjectReader objectReader;
    private final RequestHandler requestHandler;

    @JmsListener(destination = "AIRPLANES")
    public void consumeMessage(byte[] msgContent) {
        try {
            requestHandler.handleRequest(objectReader.readValue(msgContent));
        } catch (Exception e) {
            LOGGER.error("Cannot read message", e);
        }
    }
}
