package com.algo.ref.ex.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public class MessageSender implements ISender{
    @Override
    public Future<RecordMetadata> send(String message) {
        return null;
    }

    public static class SenderBuilder{
//        public SenderBuilder serverUrl(String url){
//        }
    }
}
