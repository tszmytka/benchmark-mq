package dev.tomek.benchmarkmq.manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PulsarContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_PULSAR;
import static dev.tomek.benchmarkmq.common.TestSupport.PROPS_PULSAR;

@SpringBootTest
@ActiveProfiles(COMM_PULSAR)
@Testcontainers
public class ManufacturerPulsarTest extends BaseIntegrationTest {
    @Container
    private static final PulsarContainer CONTAINER_PULSAR = new PulsarContainer(DockerImageName.parse(PROPS_PULSAR.image())).withEnv(PROPS_PULSAR.envVars());

    @Test
    void contextLoads() {
    }

    @Configuration
    static class ManufacturerPulsarTestConfig {
        @Bean
        public String pulsarServiceUrl() {
            return CONTAINER_PULSAR.getPulsarBrokerUrl();
        }
    }
}
