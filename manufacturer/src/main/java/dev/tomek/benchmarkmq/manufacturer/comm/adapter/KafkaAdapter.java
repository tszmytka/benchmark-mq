package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static dev.tomek.benchmarkmq.common.Profiles.COMM_KAFKA;

@Log4j2
@Component
@Profile(COMM_KAFKA)
@RequiredArgsConstructor
public class KafkaAdapter implements CommAdapter {
    private final KafkaProducer<byte[], byte[]> kafkaProducer;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Airplane airplane, Topic topic) {
        try {
            kafkaProducer.send(new ProducerRecord<>(topic.toString(), objectMapper.writeValueAsBytes(airplane)));
        } catch (Exception e) {
            LOGGER.error("Cannot send message", e);
        }
    }
}
