package dev.tomek.benchmarkmq.common;

public class Profiles {
    private Profiles() {
    }

    public static final String COMM_ACTIVEMQ = "comm_activemq";
    public static final String COMM_RABBITMQ = "comm_rabbitmq";
    public static final String COMM_PULSAR = "comm_pulsar";
    public static final String COMM_NATS = "comm_nats";
    public static final String COMM_REDIS = "comm_redis";
    public static final String COMM_KAFKA = "comm_kafka";
    public static final String COMM_NSQ = "comm_nsq";
    public static final String USE_SPRING_CLOUD_STREAM = "use_spring_cloud_stream";
}
