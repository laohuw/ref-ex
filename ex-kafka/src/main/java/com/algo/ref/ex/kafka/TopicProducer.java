package com.algo.ref.ex.kafka;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class TopicProducer {
    public static final short REPLICATION_FACTOR = 3;
    public static final int PARTITIONS = 6;
    public static void main(String[] args) throws IOException, URISyntaxException {
        runProducer();
    }
    public static NewTopic createTopic(String topic){
        return new NewTopic(topic, PARTITIONS, REPLICATION_FACTOR);
    }
    public static void runProducer() throws IOException, URISyntaxException {
        URL res=StreamEx.class.getClassLoader().getResource("kafka.properties");
        assert res != null;
        File fileProp= Paths.get(res.toURI()).toFile();
        Properties properties=new Properties();
        properties.load(new FileReader(fileProp));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        try (Admin adminClient = Admin.create(properties);
             Producer<String, String> producer = new KafkaProducer<>(properties)) {
            final String inputTopic = properties.getProperty("basic.input.topic");
            final String outputTopic = properties.getProperty("basic.output.topic");
            var topics = List.of(createTopic(inputTopic), createTopic(outputTopic));
            adminClient.createTopics(topics);

            Callback callback = (metadata, exception) -> {
                if (exception != null) {
                    System.out.printf("Producing records encountered error %s %n", exception);
                } else {
                    System.out.printf("Record produced - offset - %d timestamp - %d %n", metadata.offset(), metadata.timestamp());
                }
            };

            var rawRecords = List.of("orderNumber-1001",
                    "orderNumber-5000",
                    "orderNumber-999",
                    "orderNumber-3330",
                    "bogus-1",
                    "bogus-2",
                    "orderNumber-8400");
            var producerRecords = rawRecords.stream().map(r -> new ProducerRecord<>(inputTopic, "order-key", r)).collect(Collectors.toList());
            producerRecords.forEach((pr -> producer.send(pr, callback)));


        }
    }
}
