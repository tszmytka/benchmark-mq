package dev.tomek.benchmarkmq.manufacturer.cli;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.manufacturer.comm.Messenger;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
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
    private int protoPlaneCounter;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Starting Airplane manufacturer");
        final ProducingStrategy producingStrategy = new SmoothProducingStrategy(100, 10);
//        final ProducingStrategy producingStrategy = new Resilience4jProducingStrategy(rateLimiter, 10);
        producingStrategy.startProducing(() -> messenger.send(produceNewAirplane(), Topic.AIRPLANES));
    }

    private Airplane produceNewAirplane() {
        protoPlaneCounter = (protoPlaneCounter + 1) % PROTO_AIRPLANES.length;
        final Airplane proto = PROTO_AIRPLANES[protoPlaneCounter];
        return new Airplane(proto.maker(), proto.id(), System.nanoTime());
    }

    interface ProducingStrategy {
        void startProducing(Runnable production);
    }

    private static class SmoothProducingStrategy implements ProducingStrategy {
        private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
        private int limitPerSecond;
        private final int limitBump;
        private Runnable production;
        private Disposable subscription;

        private SmoothProducingStrategy(int limitPerSecond, int limitBump) {
            this.limitPerSecond = limitPerSecond;
            this.limitBump = limitBump;
        }

        @Override
        public void startProducing(Runnable production) {
            this.production = production;
            limitProduction(limitPerSecond);
            EXECUTOR.scheduleAtFixedRate(this::increaseLimit, 5, 30, TimeUnit.SECONDS);
        }

        private void increaseLimit() {
            limitPerSecond += limitBump;
            LOGGER.info("Setting rate limit to: " + limitPerSecond);
            limitProduction(limitPerSecond);
        }

        private void limitProduction(int limitPerSecond) {
            if (subscription != null) {
                subscription.dispose();
            }
            subscription = Flux.interval(Duration.ofNanos(1_000_000_000 / limitPerSecond))
                .subscribe(l -> production.run());
        }
    }

    @RequiredArgsConstructor
    private static class Resilience4jProducingStrategy implements ProducingStrategy {
        private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
        private final RateLimiter rateLimiter;
        private int limitPerSecond;
        private final int limitBump;

        @Override
        public void startProducing(Runnable production) {
            EXECUTOR.scheduleAtFixedRate(this::increaseLimit, 5, 30, TimeUnit.SECONDS);
            //noinspection InfiniteLoopStatement
            while (true) {
                rateLimiter.executeRunnable(production);
            }
        }

        private void increaseLimit() {
            limitPerSecond += limitBump;
            rateLimiter.changeLimitForPeriod(limitPerSecond);
            LOGGER.info("Setting rate limit to: " + limitPerSecond);
        }
    }
}
