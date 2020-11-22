package dev.tomek.benchmarkmq.patron;

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

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NSQ;
import static dev.tomek.benchmarkmq.common.TestSupport.PROPS_NSQ;

@SpringBootTest
@ActiveProfiles(COMM_NSQ)
@Testcontainers
@ContextConfiguration(initializers = {PatronNsqTest.Initializer.class})
public class PatronNsqTest {
    @Container
    private static final DockerComposeContainer<?> CONTAINER_NSQ = new DockerComposeContainer<>(PROPS_NSQ.dockerComposeFile())
        .withExposedService(PROPS_NSQ.serviceName(), PROPS_NSQ.port())
        .withLocalCompose(true);

    @Test
    void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of("nsq.nsqlookupd-host=" + PROPS_NSQ.serviceName()).applyTo(applicationContext);
        }
    }
}
