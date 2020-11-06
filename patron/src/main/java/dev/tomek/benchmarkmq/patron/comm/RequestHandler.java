package dev.tomek.benchmarkmq.patron.comm;

import dev.tomek.benchmarkmq.common.Airplane;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Consumer;

@Log4j2
@Component
@RequiredArgsConstructor
public class RequestHandler implements Consumer<Airplane> {
    private final Timer requestTimer;

    public void handleRequest(Airplane airplane) {
        final Duration duration = Duration.ofNanos(System.nanoTime() - airplane.dispatchNanos());
        requestTimer.record(duration);
        LOGGER.trace("Received " + airplane + " micros: " + duration.toNanos() / 1000);
    }

    @Override
    public void accept(Airplane airplane) {
        handleRequest(airplane);
    }
}
