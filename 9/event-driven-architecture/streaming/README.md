# Step 1: Get Kafka
```bash
tar -xzf kafka_2.13-4.3.1.tgz
cd kafka_2.13-4.3.1
```

# Step 2: Start the Kafka environment

1. Generate a Cluster UUID
```bash
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
```

1.  Format Log Directories
```bash
bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties
```

1.  Start the Kafka Server
```bash
bin/kafka-server-start.sh config/server.properties
```

# Step 3: Create a topic to store your events
```bash
# So before you can write your first events, you must create a topic. Open another terminal session and run:
bin/kafka-topics.sh --create --topic myTopic --bootstrap-server localhost:9092

# All of Kafka’s command line tools have additional options: run the kafka-topics.sh command without any arguments to display usage information.
bin/kafka-topics.sh --describe --topic myTopic --bootstrap-server localhost:9092
```

# Step 4: Write some events into the topic
```bash
bin/kafka-console-producer.sh --topic myTopic --bootstrap-server localhost:9092
This is my first event
This is my second event
```

# Step 5: Read the events
```bash
bin/kafka-console-consumer.sh --topic myTopic --from-beginning --bootstrap-server localhost:9092
```
