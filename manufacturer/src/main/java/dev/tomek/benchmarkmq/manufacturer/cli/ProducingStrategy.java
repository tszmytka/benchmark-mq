package dev.tomek.benchmarkmq.manufacturer.cli;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public interface ProducingStrategy {
    void startProducing(Runnable production);

    void stopProducing();


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
            LOGGER.info("Setting rate limit to: %d msgs/s".formatted(limitPerSecond));
        }

        @Override
        public void startProducing(Runnable production) {
            EXECUTOR.scheduleAtFixedRate(this::increaseLimit, 5, 30, TimeUnit.SECONDS);
        }
    }


    class Smooth extends BaseStrategy implements ProducingStrategy {
        private Disposable subscription;

        public Smooth(int limitPerSecond, int limitBump) {
            super(limitPerSecond, limitBump);
        }

        @Override
        public void startProducing(Runnable production) {
            super.startProducing(production);
            this.production = production;
            limitProduction(limitPerSecond);
        }

        @Override
        public void stopProducing() {
            EXECUTOR.shutdownNow();
            limitProduction(0);
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
            if (limitPerSecond > 0) {
                subscription = Flux.interval(Duration.ofNanos(1_000_000_000 / limitPerSecond)).subscribe(l -> production.run());
            }
        }
    }


    class Resilience4j extends BaseStrategy implements ProducingStrategy {
        private final RateLimiter rateLimiter;
        private final AtomicBoolean shouldProduce = new AtomicBoolean();

        public Resilience4j(int limitPerSecond, int limitBump, RateLimiter rateLimiter) {
            super(limitPerSecond, limitBump);
            this.rateLimiter = rateLimiter;
        }


        @SneakyThrows
        @Override
        public void startProducing(Runnable production) {
            super.startProducing(production);
            shouldProduce.set(true);
            while (shouldProduce.get()) {
                rateLimiter.executeRunnable(production);
                //noinspection BusyWait
                Thread.sleep(5);
            }
        }

        @Override
        public void stopProducing() {
            shouldProduce.set(false);
        }

        @Override
        void increaseLimit() {
            super.increaseLimit();
            rateLimiter.changeLimitForPeriod(limitPerSecond);
        }
    }


    class Manual extends BaseStrategy implements ProducingStrategy {
        private final AtomicBoolean shouldProduce = new AtomicBoolean();

        public Manual(int limitPerSecond, int limitBump) {
            super(limitPerSecond, limitBump);
        }

        @SneakyThrows
        @Override
        public void startProducing(Runnable production) {
            super.startProducing(production);
            int count = 0;
            long t0 = System.currentTimeMillis();
            shouldProduce.set(true);
            while (shouldProduce.get()) {
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

        @Override
        public void stopProducing() {
            shouldProduce.set(false);
        }
    }
}
