package dev.tomek.benchmarkmq.manufacturer.cli;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.manufacturer.comm.Messenger;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Airplane.Maker.*;

@Log4j2
@Component
@RequiredArgsConstructor
public class AirplaneManufacturer implements CommandLineRunner {
    private final Messenger messenger;
    private final RateLimiter rateLimiter;
    private final Airplane[] PROTO_AIRPLANES = {
        new Airplane(NORTH_AMERICAN_AVIATION, "P-51 Mustang", System.nanoTime()),
        new Airplane(BOEING, "777", System.nanoTime()),
        new Airplane(AIRBUS, "A380", System.nanoTime()),
    };
    private int protoPlaneCounter;
    private ProducingStrategy producingStrategy;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Starting Airplane manufacturer");

        producingStrategy = new ProducingStrategy.Smooth(100, 50);
//        producingStrategy = new ProducingStrategy.Resilience4j(100, 10, rateLimiter);
//        producingStrategy = new ProducingStrategy.Manual(100, 10);
        producingStrategy.startProducing(() -> messenger.send(produceNewAirplane(), Topic.AIRPLANES));
    }

    public void stopProducing() {
        if (producingStrategy != null) {
            producingStrategy.stopProducing();
        }
    }

    private Airplane produceNewAirplane() {
        protoPlaneCounter = (protoPlaneCounter + 1) % PROTO_AIRPLANES.length;
        final Airplane proto = PROTO_AIRPLANES[protoPlaneCounter];
        return new Airplane(proto.maker(), proto.id(), System.nanoTime());
    }
}
