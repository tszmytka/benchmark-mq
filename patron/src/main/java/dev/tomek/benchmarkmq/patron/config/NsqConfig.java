package dev.tomek.benchmarkmq.patron.config;

import com.sproutsocial.nsq.Subscriber;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.patron.comm.consumer.NsqConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NSQ;

@Configuration
@Profile(COMM_NSQ)
public class NsqConfig {
    private final String nsqlookupdHost;

    public NsqConfig(@Value("${nsq.nsqlookupd-host}") String nsqlookupdHost) {
        this.nsqlookupdHost = nsqlookupdHost;
    }

    @Bean
    public Subscriber subscriber(NsqConsumer nsqConsumer) {
        final Subscriber subscriber = new Subscriber(nsqlookupdHost);
        subscriber.subscribe(Topic.AIRPLANES.toString(), "benchmark-mq", nsqConsumer);
        return subscriber;
    }
}
