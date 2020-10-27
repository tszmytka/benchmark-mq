package dev.tomek.benchmarkmq.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public class CommonRedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("dockerhost", 6379));
    }

    @Bean
    public RedisConnection redisConnection(RedisConnectionFactory connectionFactory) {
        return connectionFactory.getConnection();
    }
}
