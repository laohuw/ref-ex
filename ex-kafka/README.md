# ex-kafka

## commands
>- **start kafka:** ~/bin/kafka_start.sh  
>- **List topics:** bin/kafka-topics.sh --list --bootstrap-server localhost:9092  
>- **Create a topic:** bin/kafka-topics.sh --create --topic event --bootstrap-server localhost:9092  
>- **Describe a topic:** bin/kafka-topics.sh --describe --topic event --bootstrap-server localhost:9092
>- **Produce a message:** bin/kafka-console-producer.sh --topic event --bootstrap-server localhost:9092
>- **Consume messages:** bin/kafka-console-consumer.sh --topic event --bootstrap-server localhost:9092 --from-beginning 
>- **File connector:** bin/kafka-console-consumer.sh --topic event --bootstrap-server localhost:9092 --from-beginning 
>- 
