package com.algo.ref.ex.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import static org.apache.kafka.streams.StreamsConfig.*;

@Slf4j
public class StreamEx {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URL res=StreamEx.class.getClassLoader().getResource("kafka.properties");
        assert res != null;
        File fileProp= Paths.get(res.toURI()).toFile();
        Properties prop=new Properties();
        prop.load(new FileReader(fileProp));
        prop.put(PROCESSING_GUARANTEE_CONFIG, EXACTLY_ONCE);

        prop.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });

        StreamsBuilder builder=new StreamsBuilder();
        Serde<String> stringSerde=Serdes.String();

//        final KStream<String, String> stream=builder.stream(prop.getProperty("input.topic.name"), Consumed.with(stringSerde, stringSerde));
//        stream.peek((k,v) -> log.info("Observed event: {}", v))
//                .mapValues(v -> v.toUpperCase())
//                .peek((k, v) -> log.info("Transformed event: {}", v))
//                .to(prop.getProperty("output.topic.name"), Produced.with(stringSerde, stringSerde));
//
//        final KTable<String, String> table=stream.toTable(Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as("test"));

        final KTable<String, String> table=builder.table(prop.getProperty("input.topic.name"), Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as("ktable-store").withKeySerde(stringSerde).withValueSerde(stringSerde));
        CharSequence orderNumberStart="orderNumber-";
        table.filter((key, value) -> value.contains(orderNumberStart))
                .mapValues(value -> value.substring(value.indexOf("-") + 1))
                .filter((key, value) -> Long.parseLong(value) > 1000)
                .toStream()
                .peek((key, value) -> log.info("Outgoing record - key " + key + " value: " + value))
                .to("testTable", Produced.with(stringSerde, stringSerde));

        Topology topology=builder.build();

        try (KafkaStreams kafkaStreams = new KafkaStreams(topology, prop)) {
            final CountDownLatch latch = new CountDownLatch(1);

            Runtime.getRuntime().addShutdownHook(new Thread(() ->{
                System.out.println("shutting down... ");
                kafkaStreams.close(Duration.ofSeconds(2));
                latch.countDown();
                    ;}));
            kafkaStreams.setStateListener((newState, oldState) -> {
                if (oldState == KafkaStreams.State.RUNNING && newState != KafkaStreams.State.RUNNING) {
                    System.out.println("new state: "+ newState);
                    latch.countDown();
                }
            });
            kafkaStreams.start();
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
