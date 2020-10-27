package dev.tomek.benchmarkmq.manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;
import static dev.tomek.benchmarkmq.common.Profiles.COMM_REDIS;

@SpringBootTest
@ActiveProfiles(COMM_REDIS)
class ManufacturerRedisTest {

    @Test
    void contextLoads() {
    }
}
