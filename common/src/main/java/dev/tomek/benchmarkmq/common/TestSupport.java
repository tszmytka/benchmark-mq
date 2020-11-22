package dev.tomek.benchmarkmq.common;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestSupport {
    public static final ContainerProperties PROPS_NATS = new ContainerProperties("nats:2.1.8", 4222);
    public static final ContainerProperties PROPS_REDIS = new ContainerProperties("redis:6.0-alpine", 6379);
    public static final ContainerProperties PROPS_RABBITMQ = new ContainerProperties("rabbitmq:3.8-management-alpine", 5672);
    public static final ContainerProperties PROPS_KAFKA = new ContainerProperties("confluentinc/cp-kafka:6.0.0", 9092);
    public static final ContainerProperties PROPS_PULSAR = new ContainerProperties("apachepulsar/pulsar:2.6.2", 6650, Map.of("PULSAR_MEM", "-Xmx128m"));
    public static final ServiceProperties PROPS_ACTIVEMQ;
    public static final ServiceProperties PROPS_NSQ;

    static {
        File dcActiveMqFile = new File("./../docker-compose.activemq.yml");
        try {
            dcActiveMqFile = dcActiveMqFile.getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PROPS_ACTIVEMQ = new ServiceProperties(dcActiveMqFile, "activemq", 61616);

        File dcNsqFile = new File("./../docker-compose.nsq.yml");
        try {
            dcNsqFile = dcNsqFile.getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PROPS_NSQ = new ServiceProperties(dcNsqFile, "nsqlookupd", 4151);
    }

    private TestSupport() {
    }

    @SuppressFBWarnings(value = "EQ_UNUSUAL", justification = "Spotbugs complains about auto-generated code")
    public record ContainerProperties(String image, int port, Map<String, String> envVars) {
        public ContainerProperties(String image, int port) {
            this(image, port, null);
        }
    }

    @SuppressFBWarnings(value = "EQ_UNUSUAL", justification = "Spotbugs complains about auto-generated code")
    public record ServiceProperties(File dockerComposeFile, String serviceName, int port) {
    }
}
