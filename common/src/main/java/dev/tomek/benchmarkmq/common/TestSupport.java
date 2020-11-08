package dev.tomek.benchmarkmq.common;

import java.util.Map;

public class TestSupport {
    public static final ContainerProperties PROPS_NATS = new ContainerProperties("nats:2.1.8", 4222);
    public static final ContainerProperties PROPS_REDIS = new ContainerProperties("redis:6.0-alpine", 6379);
    public static final ContainerProperties PROPS_RABBITMQ = new ContainerProperties("rabbitmq:3.8-management-alpine", 5672);
    public static final ContainerProperties PROPS_KAFKA = new ContainerProperties("confluentinc/cp-kafka:6.0.0", 9092);
    public static final ContainerProperties PROPS_PULSAR = new ContainerProperties("apachepulsar/pulsar:2.6.1", 6650, Map.of("PULSAR_MEM", "-Xmx128m"));

    private TestSupport() {
    }

    public record ContainerProperties(String image, int port, Map<String, String> envVars) {
        public ContainerProperties(String image, int port) {
            this(image, port, null);
        }
    }
}
