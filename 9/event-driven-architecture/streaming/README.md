# Quickstart

## Step 1: Get Kafka
```bash
tar -xzf kafka_2.13-4.3.1.tgz
cd kafka_2.13-4.3.1
```

## Step 2: Start the Kafka environment

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

## Step 3: Create a topic to store your events
```bash
# So before you can write your first events, you must create a topic. Open another terminal session and run:
bin/kafka-topics.sh --create --topic myTopic --bootstrap-server localhost:9092

# All of Kafka’s command line tools have additional options: run the kafka-topics.sh command without any arguments to display usage information.
bin/kafka-topics.sh --describe --topic myTopic --bootstrap-server localhost:9092
```

## Step 4: Write some events into the topic
```bash
bin/kafka-console-producer.sh --topic myTopic --bootstrap-server localhost:9092
This is my first event
This is my second event
```

## Step 5: Read the events
```bash
bin/kafka-console-consumer.sh --topic myTopic --from-beginning --bootstrap-server localhost:9092
```


Mostre um exemplo de uma aplicação que use distributed event streaming
====================

Um exemplo típico de **distributed event streaming** é um e-commerce. Cada serviço publica eventos no Kafka e outros serviços os consomem de forma independente, sem precisar chamar uns aos outros diretamente. Por exemplo, a criação de um pedido pode iniciar verificações de pagamento, estoque e envio em paralelo. [1,2]

```text
Aplicativo / site
       │
       ▼
  Serviço de pedidos ── publica OrderCreated ──► Kafka: orders
                                                  │
                         ┌────────────────────────┼────────────────────┐
                         ▼                        ▼                    ▼
                  Serviço de estoque       Serviço de pagamento   Serviço antifraude
                         │                        │                    │
                         └── publica eventos de resultado no Kafka ────┘
                                                  │
                                                  ▼
                                        Serviço de notificações
```

### Fluxo de exemplo

1. O serviço de pedidos grava o pedido e publica `OrderCreated`, usando o ID do pedido como chave.
2. O serviço de estoque consome o evento. Se reservar os itens, publica `InventoryReserved`; se não, publica `InventoryRejected`.
3. O serviço de pagamento consome o pedido e publica `PaymentApproved` ou `PaymentRejected`.
4. O serviço de pedidos acompanha os eventos de resultado e atualiza o estado do pedido.
5. O serviço de notificações consome os eventos relevantes e envia e-mail ou notificação ao cliente.

Os eventos podem ser organizados em tópicos como `orders`, `inventory` e `payments`. Serviços independentes podem consumir o mesmo evento sem que o produtor precise conhecer todos eles. Usar `orderId` como chave ajuda a manter, na mesma partição, os eventos relacionados a um pedido e sua ordem de processamento. [1,3]

**Exemplo simplificado em Spring Kafka:**

```java
public record OrderCreated(String orderId, String productId, int quantity) {}

@Service
public class OrderService {
    private final KafkaTemplate<String, OrderCreated> kafka;

    public OrderService(KafkaTemplate<String, OrderCreated> kafka) {
        this.kafka = kafka;
    }

    public void createOrder(OrderCreated event) {
        // Em produção, grave o pedido e publique o evento de forma confiável.
        kafka.send("orders", event.orderId(), event);
    }
}

@Component
public class InventoryService {
    @KafkaListener(topics = "orders", groupId = "inventory-service")
    public void onOrderCreated(OrderCreated event) {
        boolean reserved = reserve(event.productId(), event.quantity());

        // A lógica real também publicaria InventoryReserved ou InventoryRejected.
        System.out.println("Pedido " + event.orderId() +
                           ": estoque reservado = " + reserved);
    }

    private boolean reserve(String productId, int quantity) {
        return true; // Exemplo ilustrativo
    }
}
```

Em produção, consumidores devem tolerar reentregas, pois uma mensagem pode ser processada novamente. Também é comum usar o padrão **transactional outbox** para evitar que o pedido seja salvo no banco, mas o evento deixe de ser publicado se ocorrer uma falha entre as duas operações. [1,3]

Ler mais
* [1 - E-Commerce Streaming Architecture Patterns](https://www.conduktor.io/glossary/e-commerce-streaming-architecture-patterns)
* [2 - Event-driven architectures with Apache Kafka](https://www.redpanda.com/guides/kafka-use-cases-event-driven-architecture)
* [3 - Scaling E-commerce With Kafka Event-Driven Microservices](https://dzone.com/articles/kafka-event-driven-ecommerce-migration)


Nesse cenário os eventos que ocorrem podem ser entendidos como uma máquina de estados?
====================

Sim](**o ciclo de vida do pedido pode ser modelado como uma máquina de estados**, mas os eventos não são os estados: cada evento registra uma ocorrência que pode provocar uma transição.

Por exemplo:

```text
CRIADO
  ├─ InventoryRejected ─────────► CANCELADO
  └─ InventoryReserved ─────────► AGUARDANDO_PAGAMENTO
                                      ├─ PaymentApproved ─► CONFIRMADO
                                      └─ PaymentRejected ─► CANCELADO
```

- **Estado:** situação atual do pedido, como `AGUARDANDO_PAGAMENTO`.
- **Evento:** algo que aconteceu, como `PaymentApproved`.
- **Transição:** regra que transforma o estado em outro ao receber um evento.

Um consumidor pode aplicar essa lógica:

```java
switch (estadoAtual) {
    case "CRIADO" -> {
        if (evento instanceof InventoryReserved) {
            estadoNovo = "AGUARDANDO_PAGAMENTO";
        } else if (evento instanceof InventoryRejected) {
            estadoNovo = "CANCELADO";
        }
    }
    case "AGUARDANDO_PAGAMENTO" -> {
        if (evento instanceof PaymentApproved) {
            estadoNovo = "CONFIRMADO";
        } else if (evento instanceof PaymentRejected) {
            estadoNovo = "CANCELADO";
        }
    }
}
```

Em uma arquitetura distribuída, não há necessariamente uma única máquina de estados central. Cada serviço mantém o estado que lhe cabe, e eventos coordenam as transições](abordagem relacionada ao padrão **Saga**. Para manter o pedido coerente, defina transições válidas, trate eventos duplicados de forma idempotente e considere eventos fora de ordem ou atrasados.


Qual é a melhor forma para garantir idempotente no exemplo de uma arquitetura distribuida?
====================

A abordagem mais prática é **assumir que uma mensagem pode ser entregue mais de uma vez** e tornar o consumidor idempotente. Para isso, cada evento precisa de um `eventId` estável e o consumidor deve registrar esse ID com uma restrição `UNIQUE` no banco, **na mesma transação** em que aplica a alteração de negócio. Se o evento já foi processado, ignora-se a duplicata. Isso é conhecido como padrão **inbox**. [1,2]

Exemplo com Spring e PostgreSQL:

```sql
CREATE TABLE processed_events (
    event_id    UUID PRIMARY KEY,
    processed_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
```

```java
@Component
public class InventoryConsumer {
    private final JdbcTemplate jdbc;
    private final InventoryService inventoryService;

    public InventoryConsumer(
            JdbcTemplate jdbc,
            InventoryService inventoryService) {
        this.jdbc = jdbc;
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "orders", groupId = "inventory-service")
    public void receber(OrderCreated event) {
        processarUmaVez(event);
    }

    @Transactional
    public void processarUmaVez(OrderCreated event) {
        int inserido = jdbc.update("""
            INSERT INTO processed_events (event_id)
            VALUES (?)
            ON CONFLICT (event_id) DO NOTHING
            """, event.eventId());

        if (inserido == 0) {
            return; // Evento duplicado: já foi processado.
        }

        inventoryService.reserve(event.productId(), event.quantity());
    }
}
```

O evento deve carregar um identificador criado na origem:

```java
public record OrderCreated(
    UUID eventId,
    String orderId,
    String productId,
    int quantity
) {}
```

**Pontos importantes:**

- O registro de `eventId` e a alteração de negócio precisam estar na **mesma transação do banco**. Se a reserva falhar, o registro de deduplicação também deve ser revertido.
- Confirme o offset do Kafka somente após o processamento bem-sucedido. Assim, uma falha pode causar reentrega, mas a deduplicação evita repetir o efeito.
- O `enable.idempotence=true` no produtor ajuda a evitar duplicatas causadas por retries do próprio produtor, mas **não substitui** a idempotência do consumidor: reentregas, rebalanceamentos e reprocessamentos ainda podem acontecer. [1,3]
- Se o consumidor também atualizar o banco e publicar outro evento, considere o padrão **outbox**: grave a alteração de negócio e o evento de saída na mesma transação; um processo separado publica a outbox no Kafka. A publicação pode ser repetida, então os consumidores seguintes também devem deduplicar. [3,4]

Essa estratégia não promete “processamento exatamente uma vez” em todos os sistemas externos; ela torna os efeitos no banco seguros diante de entregas repetidas.

Ler mais
* [1 - Building Reliable Event-Driven Architectures with Kafka](https://www.javacodegeeks.com/2025/09/understanding-event-driven-architectures-kafka-outbox-pattern-and-exactly-once-guarantees.html)
* [2 - Achieving Idempotency with the Inbox Pattern](https://dev.to/actor-dev/inbox-pattern-51af)
* [3 - Consumer Patterns](Idempotency and Exactly-Once ...](https://www.trinitylogic.co.uk/blog/kafka-consumer-idempotency-exactly-once/)
* [4 - The Transactional Outbox Pattern](https://developer.confluent.io/courses/microservices/the-transactional-outbox-pattern/)


Quais são as propriedades mais importantes para configurar o Apache Kafka  no Spring framework ?
====================

No Spring Boot, as propriedades principais ficam sob `spring.kafka` no `application.yml` ou `application.properties`. As mais importantes dependem de você estar configurando produtores, consumidores ou ambos:

```yaml
spring:
  kafka:
    bootstrap-servers: kafka1:9092,kafka2:9092

    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      acks: all
      retries: 10
      properties:
        enable.idempotence: true

    consumer:
      group-id: pedidos-service
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      auto-offset-reset: earliest
      enable-auto-commit: false
      properties:
        "[spring.json.trusted.packages]": "com.exemplo.eventos"

    listener:
      ack-mode: record
      concurrency: 3
```

- `bootstrap-servers`: endereços dos brokers Kafka; configure mais de um para tolerar indisponibilidade de um broker. [1]
- **Produtor:** `key-serializer` e `value-serializer` definem como as chaves e mensagens são convertidas em bytes. `acks: all` pede confirmação das réplicas sincronizadas; `enable.idempotence: true` ajuda a evitar duplicatas em reenvios.
- **Consumidor:** `group-id` identifica o grupo que compartilha o trabalho de consumo. `key-deserializer` e `value-deserializer` precisam corresponder aos serializers usados pelo produtor.
- `auto-offset-reset`: define o comportamento quando não há offset válido; `earliest` começa pelas mensagens disponíveis mais antigas, enquanto `latest` começa pelas novas.
- `enable-auto-commit` e `listener.ack-mode`: controlam quando os offsets são confirmados. Com auto-commit desativado, o `ack-mode` permite que o Spring gerencie as confirmações conforme o processamento.
- `listener.concurrency`: número de threads de consumo; normalmente não vale a pena exceder o número de partições do tópico para aquele grupo.

Use serializers e deserializers compatíveis entre produtor e consumidor; com JSON, configure também os tipos confiáveis e os tipos de destino de acordo com sua versão do Spring Kafka. Essas opções são um ponto de partida: em produção, ajuste retries, autenticação/TLS, tratamento de falhas e estratégia de confirmação conforme a necessidade de entrega.

Ler mais
* [1 - Producer Configs | Apache Kafka](https://kafka.apache.org/43/configuration/producer-configs/)


Quais são as anotações mais importantes para enviar e receber eventos do Apache Kafka  no Spring framework ?
====================

No Spring for Apache Kafka, as principais APIs para **enviar** e **receber** eventos são `KafkaTemplate` e `@KafkaListener`. As anotações mais úteis são:

- **`@KafkaListener`**](marca um método como consumidor de mensagens de um ou mais tópicos. Pode declarar `topics` e `groupId`.
- **`@KafkaHandler`**](usada junto com `@KafkaListener` na classe para encaminhar mensagens a métodos diferentes conforme o tipo do payload.
- **`@EnableKafka`**](habilita o processamento das anotações Kafka em configurações Spring tradicionais; em aplicações Spring Boot, normalmente a configuração automática já cuida disso.
- **`@SendTo`**](indica para qual tópico enviar o resultado produzido por um listener, quando se usa processamento com resposta.
- **`@RetryableTopic`**](configura tentativas e tópicos de retry para falhas no processamento; pode ser usada com um listener.

Para **publicar** eventos, normalmente não se usa uma anotação: injeta-se `KafkaTemplate` e chama-se `send()`.

```java
@Service
public class PedidoProducer {
    private final KafkaTemplate<String, PedidoEvent> kafkaTemplate;

    public PedidoProducer(KafkaTemplate<String, PedidoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicar(PedidoEvent evento) {
        kafkaTemplate.send("pedidos", evento.getId(), evento);
    }
}
```

Um consumidor básico:

```java
@Component
public class PedidoConsumer {
    @KafkaListener(topics = "pedidos", groupId = "pedido-service")
    public void receber(PedidoEvent evento) {
        System.out.println("Pedido recebido: " + evento);
    }
}
```

Em resumo: **`KafkaTemplate` envia; `@KafkaListener` recebe**. `@KafkaHandler`, `@SendTo` e `@RetryableTopic` atendem a cenários específicos. [1]

- Exemplo completo de produtor e consumidor Kafka com Spring Boot
- Diferença entre `@KafkaListener` e `KafkaTemplate`
- Configuração de retry e dead-letter topic no Spring Kafka

Ler mais
* [1 - Intro to Apache Kafka with Spring](https://www.baeldung.com/spring-kafka)


Mostre um a  Configuração de retry e dead-letter topic no Spring Kafka
====================

Uma forma simples de configurar **retry não bloqueante** e **dead-letter topic (DLT)** é usar `@RetryableTopic` no método consumidor. O Spring encaminha a mensagem para tópicos de retry e, depois de esgotadas as tentativas, para o DLT. [1,2]

```java
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.retry.annotation.Backoff;

@Component
public class PedidoConsumer {

    @RetryableTopic(
        attempts = "4",                // 1 tentativa inicial + 3 retries
        backoff = @Backoff(
            delay = 1_000,             // primeira espera: 1 s
            multiplier = 2.0,          // espera cresce: 1 s, 2 s, 4 s
            maxDelay = 10_000
        )
    )
    @KafkaListener(topics = "pedidos", groupId = "pedido-service")
    public void receber(PedidoEvent evento) {
        processar(evento); // Se lançar exceção, o Spring aplica a política de retry.
    }

    @DltHandler
    public void tratarDlt(
            PedidoEvent evento,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topico) {
        // Registre o erro e persista ou encaminhe para análise/reprocessamento.
        System.err.println("Evento no DLT " + topico + ": " + evento);
    }

    private void processar(PedidoEvent evento) {
        // Sua lógica de negócio; lance uma exceção quando o processamento falhar.
    }
}
```

Como padrão de nomes padrão, o Spring cria tópicos de retry e um tópico final com sufixo `-dlt`, por exemplo `pedidos-dlt`. Para evitar criação automática, crie os tópicos previamente e configure `autoCreateTopics` na anotação. O método `@DltHandler` fica na mesma classe do `@KafkaListener`. [2,3]

**Atenção:** retries por tópicos não bloqueiam o tópico original, mas podem alterar a ordem de processamento dos eventos. Se a ordem estrita for requisito, considere retries bloqueantes com `DefaultErrorHandler`. [3,4]

Garanta também que o consumidor do DLT registre/persista a mensagem e seus metadados de erro. Em produção, defina quais exceções devem ser repetidas ou enviadas direto ao DLT e planeje como as mensagens serão reprocessadas.

Ler mais
* [1 - Kafka Consumer Non-Blocking Retry: Spring Retry Topics](https://www.lydtechconsulting.com/blog/kafka-spring-retry-topics)
* [2 - Dead Letter Queue for Kafka With Spring](https://www.baeldung.com/kafka-spring-dead-letter-queue)
* [3 - Kafka Spring Boot Retry Topics Demo](https://github.com/lydtechconsulting/kafka-spring-retry-topics)
* [4 - Dead letter queues in Kafka: patterns and pitfalls](https://factorhouse.io/articles/dead-letter-queues-kafka/)


Qual é a Configuração para reduzir a perda de mensagens no Apache Kafka?
====================

Para **reduzir a perda de mensagens**, configure o produtor para esperar confirmação das réplicas, habilite idempotência e use replicação adequada no tópico. No consumidor, só confirme o offset depois que o processamento terminar.

### Produtor Spring Kafka

Em `application.yml`:

```yaml
spring:
  kafka:
    producer:
      acks: all
      retries: 2147483647
      properties:
        enable.idempotence: true
        max.in.flight.requests.per.connection: 5
```

`acks=all` espera confirmação de todas as réplicas que estão sincronizadas (ISR); `enable.idempotence=true` evita duplicatas causadas por retries do produtor. Com idempotência habilitada, `max.in.flight.requests.per.connection=5` é compatível com a garantia de ordenação do produtor. [1,2]

### Tópico e brokers

Use, por exemplo, **fator de replicação 3** e `min.insync.replicas=2`:

```bash
kafka-topics.sh --bootstrap-server localhost:9092 \
  --create --topic pedidos \
  --replication-factor 3 --partitions 6 \
  --config min.insync.replicas=2
```

Assim, com `acks=all`, o Kafka exige pelo menos duas réplicas sincronizadas para aceitar gravações. Isso pode deixar o tópico temporariamente indisponível para escrita se não houver réplicas suficientes](é uma troca deliberada por durabilidade. [1,2]

### Consumidor: não confirmar antes do processamento

Para commits manuais no Spring Kafka:

```yaml
spring:
  kafka:
    consumer:
      enable-auto-commit: false
    listener:
      ack-mode: manual
```

```java
@KafkaListener(topics = "pedidos", groupId = "pedido-service")
public void receber(PedidoEvent evento, Acknowledgment ack) {
    processar(evento); // Se falhar, não confirme o offset.
    ack.acknowledge(); // Confirme só após sucesso.
}
```

Se o processo cair depois de tratar a mensagem, mas antes do commit, ela poderá ser entregue novamente. Por isso, torne o processamento **idempotente**](por exemplo, registre um ID único do evento e evite aplicar a mesma operação duas vezes. Nenhuma configuração elimina todos os riscos: também é importante monitorar falhas de envio, lag do consumidor e réplicas fora de sincronismo.

Ler mais
- [1 - Chapter 5. Kafka producer configuration tuning](https://docs.redhat.com/en/documentation/red_hat_streams_for_apache_kafka/2.2/html/kafka_configuration_tuning/con-producer-config-properties-str)
- [2 - Kafka Replication and Committed Messages](https://docs.confluent.io/kafka/design/replication.html)