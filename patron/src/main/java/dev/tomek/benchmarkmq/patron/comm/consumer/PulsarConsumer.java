package dev.tomek.benchmarkmq.patron.comm.consumer;

import com.fasterxml.jackson.databind.ObjectReader;
import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.patron.comm.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_PULSAR;

@Log4j2
@Component
@Profile(COMM_PULSAR)
@RequiredArgsConstructor
public class PulsarConsumer implements Runnable {
    private final Consumer<byte[]> msgConsumer;
    private final ObjectReader airplaneReader;
    private final RequestHandler requestHandler;
    private final AtomicBoolean shouldRun = new AtomicBoolean(true);

    @Override
    public void run() {
        while (shouldRun.get()) {
            try {
                final Message<byte[]> message = msgConsumer.receive();
                requestHandler.handleRequest(airplaneReader.readValue(message.getValue()));
            } catch (Exception e) {
                LOGGER.error("Unable to receive message", e);
            }
        }
    }

    @Component
    @Profile(COMM_PULSAR)
    @RequiredArgsConstructor
    private static class Runner implements CommandLineRunner {
        private final PulsarConsumer pulsarConsumer;

        @Override
        public void run(String... args) throws Exception {
            new Thread(pulsarConsumer).start();
        }
    }
}
