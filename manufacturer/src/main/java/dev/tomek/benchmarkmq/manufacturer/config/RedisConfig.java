package dev.tomek.benchmarkmq.manufacturer.config;

import dev.tomek.benchmarkmq.common.config.CommonRedisConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_REDIS;

@Configuration
@Profile(COMM_REDIS)
public class RedisConfig extends CommonRedisConfig {
    // example manually creating the connection
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }

    // you could also use a template
    public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
