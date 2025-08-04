package com.algo.ref.ex.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public interface ISender {
    Future<RecordMetadata> send(String message);
}
