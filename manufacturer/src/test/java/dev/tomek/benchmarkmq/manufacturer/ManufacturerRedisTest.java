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

import static dev.tomek.benchmarkmq.common.Profiles.COMM_REDIS;
import static dev.tomek.benchmarkmq.common.TestSupport.PROPS_REDIS;

@SpringBootTest
@ActiveProfiles(COMM_REDIS)
@Testcontainers
@ContextConfiguration(initializers = {ManufacturerRedisTest.Initializer.class})
class ManufacturerRedisTest extends BaseIntegrationTest {
    @Container
    private static final GenericContainer<?> CONTAINER_REDIS = new GenericContainer<>(DockerImageName.parse(PROPS_REDIS.image())).withExposedPorts(PROPS_REDIS.port());

    @Test
    void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                "spring.redis.host=" + CONTAINER_REDIS.getHost(),
                "spring.redis.port=" + CONTAINER_REDIS.getMappedPort(PROPS_REDIS.port())
            ).applyTo(applicationContext);
        }
    }
}
