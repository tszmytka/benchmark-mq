package dev.tomek.benchmarkmq.manufacturer.cli;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface ProducingStrategy {
    void startProducing(Runnable production);

    @Log4j2
    @RequiredArgsConstructor
    abstract class BaseStrategy implements ProducingStrategy {
        static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
        @NonNull
        int limitPerSecond;
        final int limitBump;
        Runnable production;

        void increaseLimit() {
            limitPerSecond += limitBump;
            LOGGER.info("Setting rate limit to: " + limitPerSecond);
        }

        @Override
        public void startProducing(Runnable production) {
            EXECUTOR.scheduleAtFixedRate(this::increaseLimit, 5, 30, TimeUnit.SECONDS);
        }
    }


    class Smooth extends BaseStrategy implements ProducingStrategy {
        private Disposable subscription;

        public Smooth(@NonNull int limitPerSecond, int limitBump) {
            super(limitPerSecond, limitBump);
        }

        @Override
        public void startProducing(Runnable production) {
            super.startProducing(production);
            this.production = production;
            limitProduction(limitPerSecond);
        }

        @Override
        void increaseLimit() {
            super.increaseLimit();
            limitProduction(limitPerSecond);
        }

        private void limitProduction(int limitPerSecond) {
            if (subscription != null) {
                subscription.dispose();
            }
            subscription = Flux.interval(Duration.ofNanos(1_000_000_000 / limitPerSecond)).subscribe(l -> production.run());
        }
    }


    class Resilience4j extends BaseStrategy implements ProducingStrategy {
        private final RateLimiter rateLimiter;

        public Resilience4j(@NonNull int limitPerSecond, int limitBump, RateLimiter rateLimiter) {
            super(limitPerSecond, limitBump);
            this.rateLimiter = rateLimiter;
        }


        @SneakyThrows
        @Override
        public void startProducing(Runnable production) {
            super.startProducing(production);
            //noinspection InfiniteLoopStatement
            while (true) {
                rateLimiter.executeRunnable(production);
                //noinspection BusyWait
                Thread.sleep(5);
            }
        }

        @Override
        void increaseLimit() {
            super.increaseLimit();
            rateLimiter.changeLimitForPeriod(limitPerSecond);
        }
    }


    class Manual extends BaseStrategy implements ProducingStrategy {

        public Manual(@NonNull int limitPerSecond, int limitBump) {
            super(limitPerSecond, limitBump);
        }

        @SneakyThrows
        @Override
        public void startProducing(Runnable production) {
            super.startProducing(production);
            int count = 0;
            long t0 = System.currentTimeMillis();
            while (true) {
                if (count++ <= limitPerSecond) {
                    production.run();
                    //noinspection BusyWait
                    Thread.sleep(5);
                } else {
                    final long t1 = System.currentTimeMillis();
                    if (t1 - t0 >= 1_000) {
                        count = 0;
                        t0 = t1;
                    }
                }
            }
        }
    }
}
