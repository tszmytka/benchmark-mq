package dev.tomek.benchmarkmq.common;

public class TestSupport {
    public static final ContainerProperties PROPS_NATS = new ContainerProperties("nats:2.1.8", 4222);
    public static final ContainerProperties PROPS_REDIS = new ContainerProperties("redis:6.0-alpine", 6379);

    private TestSupport() {
    }

    public record ContainerProperties(String image, int port) {
    }
}
