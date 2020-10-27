package dev.tomek.benchmarkmq.patron.config;

import dev.tomek.benchmarkmq.common.config.CommonRedisConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_REDIS;

@Configuration
@Profile(COMM_REDIS)
public class RedisConfig extends CommonRedisConfig {

}
