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

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;
import static dev.tomek.benchmarkmq.common.TestSupport.PROPS_ACTIVEMQ;

@SpringBootTest
@ActiveProfiles(COMM_ACTIVEMQ)
@Testcontainers
@ContextConfiguration(initializers = {ManufacturerActiveMqTest.Initializer.class})
class ManufacturerActiveMqTest extends BaseIntegrationTest {
    @Container
    private static final DockerComposeContainer<?> CONTAINER_ACTIVEMQ = new DockerComposeContainer<>(PROPS_ACTIVEMQ.dockerComposeFile())
        .withExposedService(PROPS_ACTIVEMQ.serviceName(), PROPS_ACTIVEMQ.port())
        .withLocalCompose(true);

    @Test
    void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            final String activemqBrokerUrl = "tcp://" + CONTAINER_ACTIVEMQ.getServiceHost(PROPS_ACTIVEMQ.serviceName(), PROPS_ACTIVEMQ.port())
                + ":" + CONTAINER_ACTIVEMQ.getServicePort(PROPS_ACTIVEMQ.serviceName(), PROPS_ACTIVEMQ.port());
            TestPropertyValues.of("spring.activemq.broker-url=" + activemqBrokerUrl).applyTo(applicationContext);
        }
    }
}
