package dev.tomek.benchmarkmq.patron;

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
import static dev.tomek.benchmarkmq.common.TestSupport.PROPS_NATS;

@SpringBootTest
@ActiveProfiles(COMM_NATS)
@Testcontainers
@ContextConfiguration(initializers = {PatronNatsTest.Initializer.class})
class PatronNatsTest {
    @Container
    private static final GenericContainer<?> nats = new GenericContainer<>(DockerImageName.parse(PROPS_NATS.image()))/*.withExposedPorts(PROPS_NATS.port())*/;

    @Test
    void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            final String natsHost = nats.getHost() + ":" + nats.getMappedPort(PROPS_NATS.port());
            TestPropertyValues.of("nats.spring.server=" + natsHost).applyTo(applicationContext);
        }
    }
}
