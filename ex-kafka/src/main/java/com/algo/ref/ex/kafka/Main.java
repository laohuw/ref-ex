package com.algo.ref.ex.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

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
                .groupId("ex_kafka1");
        AtomicBoolean bStop=new AtomicBoolean(false);
        Thread consumerThread=new Thread(){
            public void run(){
                KafkaConsumer consumer=kafka.getKafkaConsumer();
                while(!bStop.get()){
                    ConsumerRecords<String, String> records=consumer.poll(Duration.ofMillis(100));
                    for(ConsumerRecord<String, String> record : records){
                        System.out.printf(ANSI_RED+"Received offset= %d, key = %s, value= %s%n ",record.offset(), record.key(), record.value());
                    }
                }
            }
        };
        consumerThread.start();
        Thread.sleep(1000);
        bStop.set(true);

//        for(int i=0; i<10; i++) {
//            Future<RecordMetadata> result = kafka.send("Test"+i);
//            try {
//                RecordMetadata recordMetadata = result.get(10, TimeUnit.SECONDS);
//                System.out.println(ANSI_GREEN+"Record meta data, offset: "+ recordMetadata.offset()+", partition: "+recordMetadata.partition());
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (ExecutionException e) {
//                throw new RuntimeException(e);
//            } catch (TimeoutException e) {
//                throw new RuntimeException(e);
//            }
//        }

// timer task
//        TimerTask task=new TimerTask() {
//            @Override
//            public void run() {
//                bStop.set(true);
//            }
//        };
//        Timer timer=new Timer("timer");
//        timer.schedule(task, 2000);
    }
}
