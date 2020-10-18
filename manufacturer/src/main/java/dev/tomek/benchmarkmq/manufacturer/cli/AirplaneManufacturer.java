package dev.tomek.benchmarkmq.manufacturer.cli;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.manufacturer.comm.Messenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static dev.tomek.benchmarkmq.common.Airplane.Maker.NORTH_AMERICAN_AVIATION;

@Log4j2
@Component
@RequiredArgsConstructor
public class AirplaneManufacturer implements CommandLineRunner {
    private static final int MSG_PER_SEC = 2;
    private final Messenger messenger;

    @Override
    public void run(String... args) throws Exception {
        Flux.interval(Duration.ofMillis(1000 / MSG_PER_SEC))
            .subscribe(l -> messenger.send(new Airplane(NORTH_AMERICAN_AVIATION, "P-51 Mustang", System.nanoTime()), Topic.AIRPLANES));
    }
}
