package dev.tomek.benchmarkmq.patron.config;

import com.sproutsocial.nsq.Subscriber;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.patron.comm.consumer.NsqConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_NSQ;

@Configuration
@Profile(COMM_NSQ)
public class NsqConfig {
    @Bean
    public Subscriber subscriber(NsqConsumer nsqConsumer) {
        final Subscriber subscriber = new Subscriber("dockerhost");
        subscriber.subscribe(Topic.AIRPLANES.toString(), "benchmark-mq", nsqConsumer);
        return subscriber;
    }
}
