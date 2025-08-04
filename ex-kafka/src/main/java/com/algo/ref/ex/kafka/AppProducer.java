package com.algo.ref.ex.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AppProducer {

    public static void main(String[] args) throws InterruptedException {
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        Kafka kafka=new Kafka();
        kafka.serverUrl("localhost:9092")
                .defaultTopic("event")
                .keySerializer(StringSerializer.class)
                .valueSerializer(StringSerializer.class)
                .keyDeSerializer(StringDeserializer.class)
                .valueDeSerializer(StringDeserializer.class)
                .resetOffset("earliest")
                .autoCommit(false)
                .commitInterval("1000")
                .groupId("ex_kafka")
                .setProperties(Map.of("acks", "all", "retries", "3"));


        for(int i=0; i<10; i++) {
            Future<RecordMetadata> result = kafka.send("Test"+i);
            try {
                RecordMetadata recordMetadata = result.get(10, TimeUnit.SECONDS);
                System.out.println(ANSI_GREEN+"Record meta data, offset: "+ recordMetadata.offset()+", partition: "+recordMetadata.partition());

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
