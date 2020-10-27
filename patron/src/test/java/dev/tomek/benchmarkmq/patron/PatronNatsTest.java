package dev.tomek.benchmarkmq.patron;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;
import static dev.tomek.benchmarkmq.common.Profiles.COMM_RABBITMQ;

@SpringBootTest
@ActiveProfiles(COMM_NATS)
class PatronNatsTest {

    @Test
    void contextLoads() {
    }
}
