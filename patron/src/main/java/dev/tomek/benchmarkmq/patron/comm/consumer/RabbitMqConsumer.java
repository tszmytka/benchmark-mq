package dev.tomek.benchmarkmq.patron.comm.consumer;

import com.fasterxml.jackson.databind.ObjectReader;
import dev.tomek.benchmarkmq.patron.comm.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_RABBITMQ;

@Log4j2
@Component
@Profile(COMM_RABBITMQ)
@RequiredArgsConstructor
public class RabbitMqConsumer implements MessageListener {
    private final ObjectReader objectReader;
    private final RequestHandler requestHandler;

    @Override
    public void onMessage(Message message) {
        try {
            requestHandler.handleRequest(objectReader.readValue(message.getBody()));
        } catch (Exception e) {
            LOGGER.error("Cannot read message", e);
        }
    }
}
