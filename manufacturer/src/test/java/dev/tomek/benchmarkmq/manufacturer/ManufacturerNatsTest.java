package dev.tomek.benchmarkmq.manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NATS;

@SpringBootTest
@ActiveProfiles(COMM_NATS)
@Testcontainers
@ContextConfiguration(initializers = {ManufacturerNatsTest.Initializer.class})
class ManufacturerNatsTest {
    private static final int NATS_PORT = 4222;

    @Container
    public static GenericContainer<?> nats = new GenericContainer<>(DockerImageName.parse("nats:2.1.8"))/*.withExposedPorts(NATS_PORT)*/;

    @Test
    void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            final String natsHost = nats.getHost() + ":" + nats.getMappedPort(NATS_PORT);
            TestPropertyValues.of("nats.spring.server=" + natsHost).applyTo(applicationContext);
        }
    }
}
