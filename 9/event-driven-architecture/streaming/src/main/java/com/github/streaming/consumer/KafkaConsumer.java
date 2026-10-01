package com.github.streaming.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "myTopic", groupId = "my-group")
    public void listen(String message) {

        System.out.println("Received from Kafka: " + message);

    }

}
