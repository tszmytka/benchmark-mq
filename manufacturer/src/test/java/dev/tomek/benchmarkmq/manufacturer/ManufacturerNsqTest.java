package dev.tomek.benchmarkmq.manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NSQ;

@SpringBootTest
@ActiveProfiles(COMM_NSQ)
public class ManufacturerNsqTest {
    @Test
    void contextLoads() {
    }
}
