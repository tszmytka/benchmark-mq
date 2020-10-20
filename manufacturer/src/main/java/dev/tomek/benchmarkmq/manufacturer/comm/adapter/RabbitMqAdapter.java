package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RequiredArgsConstructor
public class RabbitMqAdapter implements CommAdapter {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(Airplane airplane, Topic topic) {
        rabbitTemplate.convertAndSend(topic.toString(), "airplane hello world");
    }
}
