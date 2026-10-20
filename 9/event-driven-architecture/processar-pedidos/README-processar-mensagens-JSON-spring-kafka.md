Esta conversa foi gerada com o Duck.ai (https://duck.ai) usando o modelo GPT-6 Luna da OpenAI. Os chats com IA podem apresentar informações incorretas ou ofensivas (consulta https://duckduckgo.com/duckai/privacy-terms para obter mais informações).

====================

Pedido do utilizador 1 de 2 - 09/10/2026, 10:25:00:
mostre exemplo sobre como processar mensagens JSON no apache Kafka
use spring boot

GPT-6 Luna:
A forma mais simples é deixar o Spring Kafka converter o JSON para um objeto Java. Este exemplo recebe um evento do tópico `pedidos` e o processa com `@KafkaListener`. O Spring Kafka oferece serialização e desserialização JSON e integração com `KafkaTemplate` e `@KafkaListener`. [1,2]

**1. Dependência Maven**

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

**2. Configure o consumidor** em `application.yml`:

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: pedidos-app
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        "[spring.json.value.default.type]": com.exemplo.Pedido
        "[spring.json.trusted.packages]": com.exemplo
```

`value.default.type` informa a classe Java para a qual o JSON será convertido. Limite `trusted.packages` aos pacotes que você controla.

**3. Crie o modelo**

```java
package com.exemplo;

import java.math.BigDecimal;

public record Pedido(String id, String cliente, BigDecimal valor) {}
```

Exemplo de mensagem JSON:

```json
{
  "id": "p-123",
  "cliente": "Ana",
  "valor": 89.90
}
```

**4. Processe as mensagens**

```java
package com.exemplo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @KafkaListener(topics = "pedidos")
    public void processar(Pedido pedido) {
        System.out.printf(
            "Pedido %s de %s: R$ %s%n",
            pedido.id(),
            pedido.cliente(),
            pedido.valor()
        );

        // Aqui você pode validar o pedido e chamar sua lógica de negócio.
    }
}
```

Com o broker Kafka rodando em `localhost:9092`, publique a mensagem JSON no tópico `pedidos`. O Spring a desserializa como `Pedido` e chama `processar`. Para publicar objetos Java como JSON, use `KafkaTemplate<String, Pedido>` configurado com `JsonSerializer`. [2]

Ler mais
[1] Intro to Apache Kafka with Spring — https://www.baeldung.com/spring-kafka
[2] Kafka JSON Serialization — https://www.lydtechconsulting.com/blog/kafka-json-serialization