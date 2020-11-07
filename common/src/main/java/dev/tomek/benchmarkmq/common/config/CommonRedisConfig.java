package dev.tomek.benchmarkmq.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

public class CommonRedisConfig {
    @Bean
    public RedisConnection redisConnection(RedisConnectionFactory connectionFactory) {
        return connectionFactory.getConnection();
    }
}
