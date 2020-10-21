package dev.tomek.benchmarkmq.manufacturer.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ManufacturerConfig {
    @Bean
    public RateLimiter rateLimiter() {
        final RateLimiterConfig rlConfig = RateLimiterConfig.custom()
            .limitForPeriod(1)
            .limitRefreshPeriod(Duration.ofSeconds(1))
            .timeoutDuration(Duration.ofSeconds(1))
            .build();
        return RateLimiterRegistry.ofDefaults().rateLimiter("rl0", rlConfig);
    }
}
