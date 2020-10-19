package dev.tomek.benchmarkmq.patron.comm.consumer;

import com.fasterxml.jackson.databind.ObjectReader;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.patron.comm.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_KAFKA;

@Log4j2
@Component
@Profile(COMM_KAFKA)
@RequiredArgsConstructor
class KafkaAppConsumer implements Runnable {
    private final KafkaConsumer<byte[], byte[]> kafkaConsumer;
    private final RequestHandler requestHandler;
    private final ObjectReader requestReader;
    private final Topic currentTopic;
    private final AtomicBoolean shouldRun = new AtomicBoolean(true);

    @Override
    public void run() {
        kafkaConsumer.subscribe(List.of(currentTopic.toString()));
        while (shouldRun.get()) {
            kafkaConsumer.poll(Duration.ofSeconds(1)).forEach(r -> {
                try {
                    requestHandler.handleRequest(requestReader.readValue(r.value()));
                } catch (Exception e) {
                    LOGGER.error("Cannot receive message", e);
                }
            });

        }
    }

    @Component
    @Profile(COMM_KAFKA)
    @RequiredArgsConstructor
    private static class Runner implements CommandLineRunner {
        private final KafkaAppConsumer kafkaAppConsumer;

        @Override
        public void run(String... args) throws Exception {
            new Thread(kafkaAppConsumer).start();
        }
    }
}
