package dev.tomek.benchmarkmq.manufacturer.comm;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;
import dev.tomek.benchmarkmq.manufacturer.comm.adapter.CommAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Messenger {
    private final CommAdapter commAdapter;

    public void send(Airplane airplane, Topic topic) {
        commAdapter.send(airplane, topic);
    }
}
