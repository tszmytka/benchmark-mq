package dev.tomek.benchmarkmq.patron;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;

@SpringBootTest
@ActiveProfiles(COMM_ACTIVEMQ)
class PatronActiveMqTest {

    @Test
    @Disabled
    void contextLoads() {
    }
}
