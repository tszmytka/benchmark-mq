package dev.tomek.benchmarkmq.patron.comm.consumer;

import com.fasterxml.jackson.databind.ObjectReader;
import com.sproutsocial.nsq.Message;
import com.sproutsocial.nsq.MessageHandler;
import dev.tomek.benchmarkmq.patron.comm.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NSQ;

@Log4j2
@Component
@Profile(COMM_NSQ)
@RequiredArgsConstructor
public class NsqConsumer implements MessageHandler {
    private final RequestHandler requestHandler;
    private final ObjectReader airplaneReader;

    @Override
    public void accept(Message msg) {
        try {
            requestHandler.handleRequest(airplaneReader.readValue(msg.getData()));
        } catch (Exception e) {
            LOGGER.error("could not handle message", e);
        }
    }
}
