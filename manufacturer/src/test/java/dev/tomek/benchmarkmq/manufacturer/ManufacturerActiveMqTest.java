package dev.tomek.benchmarkmq.manufacturer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_ACTIVEMQ;

@SpringBootTest
@ActiveProfiles(COMM_ACTIVEMQ)
@Testcontainers
class ManufacturerActiveMqTest {
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

    // com.github.dockerjava.api.exception.InternalServerErrorException: Status 500: {"message":"invalid volume specification: 'D:/development/java/benchmark-mq/:/d/development/java/benchmark-mq/:rw'"}
    @Container
    private static final DockerComposeContainer<?> CONTAINER_ACTIVEMQ = new DockerComposeContainer<>(DC_FILE)
        .withExposedService("activemq", 61616);

    @Test
    @Disabled
    void contextLoads() {
    }
}
