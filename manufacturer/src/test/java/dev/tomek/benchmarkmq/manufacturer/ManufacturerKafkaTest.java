package dev.tomek.benchmarkmq.manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_KAFKA;
import static dev.tomek.benchmarkmq.common.TestSupport.PROPS_KAFKA;

@SpringBootTest
@ActiveProfiles(COMM_KAFKA)
@Testcontainers
public class ManufacturerKafkaTest extends BaseIntegrationTest {
    @Container
    private static final KafkaContainer CONTAINER_KAFKA = new KafkaContainer(DockerImageName.parse(PROPS_KAFKA.image()));

    @Test
    void contextLoads() {
    }

    @Configuration
    static class ManufacturerKafkaTestConfig {
        @Bean
        public String kafkaBootstrapServers() {
            return CONTAINER_KAFKA.getBootstrapServers();
        }
    }
}
