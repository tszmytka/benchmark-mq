package dev.tomek.benchmarkmq.manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_RABBITMQ;
import static dev.tomek.benchmarkmq.common.TestSupport.PROPS_RABBITMQ;

@SpringBootTest
@ActiveProfiles(COMM_RABBITMQ)
@Testcontainers
@ContextConfiguration(initializers = {ManufacturerRabbitMqTest.Initializer.class})
class ManufacturerRabbitMqTest extends BaseIntegrationTest {
    @Container
    private static final RabbitMQContainer CONTAINER_RABBITMQ = new RabbitMQContainer(PROPS_RABBITMQ.image()).withExposedPorts(PROPS_RABBITMQ.port());

    @Test
    void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                "spring.rabbitmq.host=" + CONTAINER_RABBITMQ.getHost(),
                "spring.rabbitmq.port=" + CONTAINER_RABBITMQ.getMappedPort(PROPS_RABBITMQ.port())
            ).applyTo(applicationContext);
        }
    }
}
