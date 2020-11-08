package dev.tomek.benchmarkmq.manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;

@SpringBootTest
@ActiveProfiles(COMM_ACTIVEMQ)
@Testcontainers
@ContextConfiguration(initializers = {ManufacturerActiveMqTest.Initializer.class})
class ManufacturerActiveMqTest {
    private static final String SERVICE_ACTIVEMQ = "activemq";
    private static final int ACTIVEMQ_PORT = 61616;
    private static final File DC_FILE;

    static {
        File dcFile = new File("./../docker-compose.activemq.yml");
        try {
            dcFile = dcFile.getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DC_FILE = dcFile;
    }

    @Container
    private static final DockerComposeContainer<?> CONTAINER_ACTIVEMQ = new DockerComposeContainer<>(DC_FILE)
        .withExposedService(SERVICE_ACTIVEMQ, ACTIVEMQ_PORT)
        .withLocalCompose(true);

    @Test
    void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            final String activemqBrokerUrl = "tcp://" + CONTAINER_ACTIVEMQ.getServiceHost(SERVICE_ACTIVEMQ, ACTIVEMQ_PORT)
                + ":" + CONTAINER_ACTIVEMQ.getServicePort(SERVICE_ACTIVEMQ, ACTIVEMQ_PORT);
            TestPropertyValues.of("spring.activemq.broker-url=" + activemqBrokerUrl).applyTo(applicationContext);
        }
    }
}
