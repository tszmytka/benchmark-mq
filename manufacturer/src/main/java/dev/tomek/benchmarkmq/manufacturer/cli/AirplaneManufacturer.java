package dev.tomek.benchmarkmq.manufacturer.cli;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.manufacturer.comm.Messenger;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private ScheduledExecutorService scheduledExecutor;
    private int rateLimit;
    private int rateLimitAutoIncrease;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Starting Airplane manufacturer");
        int i = 0;

        rateLimit = 500;
        setRateLimitAutoIncrease(0);

        //noinspection InfiniteLoopStatement
        while (true) {
            final Airplane proto = PROTO_AIRPLANES[i];
            rateLimiter.executeRunnable(() -> messenger.send(new Airplane(proto.maker(), proto.id(), System.nanoTime()), Topic.AIRPLANES));
            i = (i + 1) % PROTO_AIRPLANES.length;
        }
    }

    private void setRateLimitAutoIncrease(int rateLimitAutoIncrease) {
        this.rateLimitAutoIncrease = rateLimitAutoIncrease;
        if (scheduledExecutor == null || scheduledExecutor.isShutdown()) {
            scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutor.scheduleAtFixedRate(this::increaseLimit, 5, 30, TimeUnit.SECONDS);
        }
    }

    private void increaseLimit() {
        rateLimit += rateLimitAutoIncrease;
        rateLimiter.changeLimitForPeriod(rateLimit);
        LOGGER.info("Setting rate limit to: " + rateLimit);
    }
}
