package dev.tomek.benchmarkmq.manufacturer;

import dev.tomek.benchmarkmq.manufacturer.cli.AirplaneManufacturer;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseIntegrationTest {
    @Autowired
    private AirplaneManufacturer airplaneManufacturer;

    @AfterEach
    void tearDown() {
        airplaneManufacturer.stopProducing();
    }
}
