package com.github.streaming;

import com.github.streaming.producer.KafkaProducer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class StreamingApplication {

	public static void main(String[] args) {

		SpringApplication.run(StreamingApplication.class, args);
	}

//	@Bean
//	public ApplicationRunner runner(KafkaTemplate<String, String> template) {
//
//		return args -> {
//			template.send("myTopic", "Hello World!");
//		};
//
//	}

}
