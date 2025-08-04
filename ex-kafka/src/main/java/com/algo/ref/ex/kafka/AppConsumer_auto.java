package com.algo.ref.ex.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppConsumer_auto {

    public static void main(String[] args) throws InterruptedException {
        ex_auto_commit();

    }

    private static void ex_auto_commit() throws InterruptedException {
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        Kafka kafka = new Kafka();
        kafka.serverUrl("localhost:9092")
                .defaultTopic("event")
                .keySerializer(StringSerializer.class)
                .valueSerializer(StringSerializer.class)
                .keyDeSerializer(StringDeserializer.class)
                .valueDeSerializer(StringDeserializer.class)
                .resetOffset("earliest")
                .autoCommit(true)
                .commitInterval("100")
                .groupId("ex_auto_commit")
        ;
        AtomicBoolean bStop = new AtomicBoolean(false);
        Thread consumerThread = new Thread() {
            public void run() {
                KafkaConsumer consumer = kafka.getKafkaConsumer();
                while (!bStop.get()) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf(ANSI_RED + "Received offset= %d, key = %s, value= %s%n ",
                                record.offset(), record.key(), record.value());
                    }
                }
            }
        };
        consumerThread.start();
        Thread.sleep(2000);
        bStop.set(true);
    }
    private static void sync_manual_commit() throws InterruptedException {
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        Kafka kafka = new Kafka();
        kafka.serverUrl("localhost:9092")
                .defaultTopic("event")
                .keySerializer(StringSerializer.class)
                .valueSerializer(StringSerializer.class)
                .keyDeSerializer(StringDeserializer.class)
                .valueDeSerializer(StringDeserializer.class)
//                .resetOffset("earliest")
                .autoCommit(false)
                .groupId("sync_manual_commit")
        ;
        AtomicBoolean bStop = new AtomicBoolean(false);
        Thread consumerThread = new Thread() {
            public void run() {
                KafkaConsumer consumer = kafka.getKafkaConsumer();
                while (!bStop.get()) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf(ANSI_RED + "Received partition=%d offset= %d, key = %s, value= %s%n ",
                                record.partition(), record.offset(), record.key(), record.value());
                    }
                    try{
                        consumer.commitSync();
                    }catch (CommitFailedException e){
                        System.out.println("Commit failed duo: " +e );
                        e.printStackTrace();
                    }
                }
            }
        };
        consumerThread.start();
        Thread.sleep(2000);
        bStop.set(true);
    }

    private static void async_manual_commit() throws InterruptedException {
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        Kafka kafka = new Kafka();
        kafka.serverUrl("localhost:9092")
                .defaultTopic("event")
                .keySerializer(StringSerializer.class)
                .valueSerializer(StringSerializer.class)
                .keyDeSerializer(StringDeserializer.class)
                .valueDeSerializer(StringDeserializer.class)
//                .resetOffset("earliest")
                .autoCommit(false)
                .groupId("aync_manual_commit")
        ;
        AtomicBoolean bStop = new AtomicBoolean(false);
        Thread consumerThread = new Thread() {
            public void run() {
                KafkaConsumer consumer = kafka.getKafkaConsumer();
                while (!bStop.get()) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf(ANSI_RED + "Received offset= %d, key = %s, value= %s%n ",
                                record.offset(), record.key(), record.value());
                    }
                    try{
                        consumer.commitAsync();
                    }catch (CommitFailedException e){
                        System.out.println("Commit failed duo: " +e );
                        e.printStackTrace();
                    }
                }
            }
        };
        consumerThread.start();
        Thread.sleep(2000);
        bStop.set(true);
    }

    private static void commit_offset_commit() throws InterruptedException {
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        Map<TopicPartition, OffsetAndMetadata> offsetAndMetadataMap=new HashMap<>();
        Kafka kafka = new Kafka();
        kafka.serverUrl("localhost:9092")
                .defaultTopic("event")
                .keySerializer(StringSerializer.class)
                .valueSerializer(StringSerializer.class)
                .keyDeSerializer(StringDeserializer.class)
                .valueDeSerializer(StringDeserializer.class)
//                .resetOffset("earliest")
                .autoCommit(false)
                .groupId("commit_manual_commit")
        ;
        AtomicBoolean bStop = new AtomicBoolean(false);

        Thread consumerThread = new Thread() {
            public void run() {
                KafkaConsumer consumer = kafka.getKafkaConsumer();
                while (!bStop.get()) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf(ANSI_RED + "Received offset= %d, key = %s, value= %s%n ",
                                record.offset(), record.key(), record.value());
                        try{
                            TopicPartition topicPartition=new TopicPartition(record.topic(), record.partition());
                            OffsetAndMetadata offsetAndMetadata=new OffsetAndMetadata(record.offset());
                            offsetAndMetadataMap.put(topicPartition, offsetAndMetadata);
                            consumer.commitSync(offsetAndMetadataMap);
                        }catch (CommitFailedException e){
                            System.out.println("Commit failed duo: " +e );
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        consumerThread.start();
        Thread.sleep(2000);
        bStop.set(true);
    }
}
