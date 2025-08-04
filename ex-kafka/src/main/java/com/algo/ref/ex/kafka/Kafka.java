package com.algo.ref.ex.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class Kafka implements ISender, IReceiver{
    private Properties kafkaProp=new Properties();
    private KafkaProducer kafkaProducer;
    private String defaultTopic;
    private KafkaConsumer kafkaConsumer;

    public Kafka serverUrl(String url){
        this.kafkaProp.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, url);
        return this;
    }
    public Kafka keySerializer(Class c){
        this.kafkaProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, c);
        return this;
    }
    public Kafka valueSerializer(Class c){
        this.kafkaProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, c);
        return this;
    }
    public Kafka keyDeSerializer(Class c){
        this.kafkaProp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, c);
        return this;
    }
    public Kafka valueDeSerializer(Class c){
        this.kafkaProp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, c);
        return this;
    }
    public Kafka groupId(String groupId){
        this.kafkaProp.put(CommonClientConfigs.GROUP_ID_CONFIG, groupId);
        return this;
    }
    public Kafka defaultTopic(String topic){
        this.defaultTopic=topic;
        return this;
    }
    public Kafka autoCommit(Boolean autoCommit){
        this.kafkaProp.put(ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        return this;
    }

    public Kafka resetOffset(String offset){
        this.kafkaProp.put(AUTO_OFFSET_RESET_CONFIG, offset);
        return this;
    }
    public Kafka commitInterval(String ms){
        this.kafkaProp.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, ms);
        return this;
    }

    public Kafka setProperties(Map<String, String> prop){
        this.kafkaProp.putAll(prop);
        return this;
    }
    public <T> T build (Class<T> t){
        if (t == KafkaConsumer.class){
            KafkaConsumer consumer=new KafkaConsumer<>(kafkaProp);
            return (T)consumer;
        }else if( t== KafkaProducer.class){
            KafkaProducer producer=new KafkaProducer(kafkaProp);
            return (T)producer;
        }else
            return null;
    };

    public KafkaConsumer getKafkaConsumer(){
        if(kafkaConsumer==null) {
            kafkaConsumer = build(KafkaConsumer.class);
            kafkaConsumer.subscribe(Arrays.asList(this.defaultTopic));
        }
        return kafkaConsumer;
    }

    @Override
    public Boolean receive(Consumer<String> callback) {
        KafkaConsumer consumer=getKafkaConsumer();
//        consumer.
        return true;
    }

    public String getTopic(){
        return this.defaultTopic;
    }
    @Override
    public Future<RecordMetadata> send(String message) {
        KafkaProducer producer=getProducer();
        ProducerRecord record=new ProducerRecord(this.defaultTopic, message);
        Future<RecordMetadata> result= producer.send(record);
        return result;
    }

    public KafkaProducer getProducer() {
        if(kafkaProducer==null)
            kafkaProducer=build(KafkaProducer.class);
        return kafkaProducer;
    }
}
