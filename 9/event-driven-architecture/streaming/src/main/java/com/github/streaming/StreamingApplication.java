package com.github.streaming;

import com.github.streaming.producer.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamingApplication {

	public static void main(String[] args) {

		SpringApplication.run(StreamingApplication.class, args);

		KafkaProducer producer = new KafkaProducer();

		producer.send("Hello World!");
		producer.send("Apache Kafka");
		producer.send("Kafka");
		producer.send("is");
		producer.send("a");
		producer.send("distributed");
		producer.send("event");
		producer.send("streaming");
		producer.send("platform");
		producer.send("used");
		producer.send("for");
		producer.send("high-throughput");
		producer.send("and");
		producer.send("fault-tolerant");
		producer.send("messaging.");

	}

}
