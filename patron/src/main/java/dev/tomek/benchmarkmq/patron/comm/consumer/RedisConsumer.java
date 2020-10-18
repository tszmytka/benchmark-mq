package dev.tomek.benchmarkmq.patron.comm.consumer;

import com.fasterxml.jackson.databind.ObjectReader;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.patron.comm.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_REDIS;

@Log4j2
@Component
@Profile(COMM_REDIS)
@RequiredArgsConstructor
public class RedisConsumer implements MessageListener {
    private final RequestHandler requestHandler;
    private final ObjectReader airplaneReader;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            requestHandler.handleRequest(airplaneReader.readValue(message.getBody()));
        } catch (Exception e) {
            LOGGER.error("Could not handle message", e);
        }
    }

    @Component
    @Profile(COMM_REDIS)
    @RequiredArgsConstructor
    private static class Runner implements CommandLineRunner {
        private final RedisConnection redisConnection;
        private final RedisConsumer redisConsumer;

        @Override
        public void run(String... args) throws Exception {
            new Thread(() -> redisConnection.subscribe(redisConsumer, Topic.AIRPLANES.toString().getBytes())).start();
        }
    }
}
