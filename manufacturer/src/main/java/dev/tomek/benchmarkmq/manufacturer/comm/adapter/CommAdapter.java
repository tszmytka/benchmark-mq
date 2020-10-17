package dev.tomek.benchmarkmq.manufacturer.comm.adapter;

import dev.tomek.benchmarkmq.common.Airplane;
import dev.tomek.benchmarkmq.common.Topic;

public interface CommAdapter {
    void send(Airplane airplane, Topic topic);
}
