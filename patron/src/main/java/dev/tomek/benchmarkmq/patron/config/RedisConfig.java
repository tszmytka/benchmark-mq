package dev.tomek.benchmarkmq.patron.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_REDIS;

@Configuration
@Profile(COMM_REDIS)
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("dockerhost", 6379));
    }

    @Bean
    public RedisConnection redisConnection(RedisConnectionFactory connectionFactory) {
        return connectionFactory.getConnection();
    }
}
