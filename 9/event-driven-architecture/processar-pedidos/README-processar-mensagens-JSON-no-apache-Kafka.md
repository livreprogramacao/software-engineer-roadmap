Esta conversa foi gerada com o Duck.ai (https://duck.ai) usando o modelo GPT-6 Luna da OpenAI. Os chats com IA podem apresentar informações incorretas ou ofensivas (consulta https://duckduckgo.com/duckai/privacy-terms para obter mais informações).

====================

Pedido do utilizador 1 de 1 - 09/10/2026, 08:51:21:
mostre exemplo sobre como processar mensagens JSON no apache Kafka
use java

GPT-6 Luna:
A forma mais simples é enviar o JSON como uma `String` e convertê-lo em um objeto Java no consumidor. Este exemplo usa Kafka e Jackson.

Dependências Maven:

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>4.1.0</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.20.0</version>
    </dependency>
</dependencies>
```

Defina uma classe para representar a mensagem:

```java
public record Pedido(String id, String cliente, double total) {}
```

**Produtor:** serializa o objeto para JSON e publica no tópico `pedidos`.

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProdutorPedidos {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        ObjectMapper mapper = new ObjectMapper();

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            Pedido pedido = new Pedido("p-123", "Ana", 89.90);
            String json = mapper.writeValueAsString(pedido);

            ProducerRecord<String, String> record =
                    new ProducerRecord<>("pedidos", pedido.id(), json);

            producer.send(record).get(); // aguarda confirmação do broker
            System.out.println("Enviado: " + json);
        }
    }
}
```

**Consumidor:** lê a mensagem, converte o JSON em `Pedido` e processa o objeto.

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumidorPedidos {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "processador-pedidos");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ObjectMapper mapper = new ObjectMapper();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(List.of("pedidos"));

            while (true) {
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(500));

                for (ConsumerRecord<String, String> record : records) {
                    try {
                        Pedido pedido = mapper.readValue(record.value(), Pedido.class);
                        System.out.printf(
                                "Processando pedido %s: cliente=%s, total=%.2f%n",
                                pedido.id(), pedido.cliente(), pedido.total()
                        );

                        // Faça aqui o processamento do pedido.
                    } catch (Exception e) {
                        System.err.println("JSON inválido: " + record.value());
                        // Em produção, considere enviar a mensagem para um tópico de erro.
                    }
                }

                // Confirma os offsets depois de processar o lote.
                if (!records.isEmpty()) {
                    consumer.commitSync();
                }
            }
        }
    }
}
```

O `StringSerializer`/`StringDeserializer` trata o valor como texto; o Jackson faz a conversão entre JSON e `Pedido`. Para rodar o exemplo, Kafka precisa estar disponível em `localhost:9092`, e o tópico `pedidos` deve existir.