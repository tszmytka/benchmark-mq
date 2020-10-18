package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_REDIS;

@Log4j2
@Component
@Profile(COMM_REDIS)
@RequiredArgsConstructor
public class RedisAdapter implements CommAdapter {
    private final RedisConnection redisConnection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Airplane airplane, Topic topic) {
        try {
            redisConnection.publish(topic.toString().getBytes(), objectMapper.writeValueAsBytes(airplane));
        } catch (Exception e) {
            LOGGER.error("Could not send message", e);
        }
    }
}
