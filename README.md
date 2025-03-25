## Kafka-Starter
Этот стартер предназначен для упрощения интеграции [функциональности] в проекты Spring Boot. Он предоставляет готовые конфигурации, бины и зависимости, позволяя быстро начать работу без лишних настроек.

## Установка
Добавьте зависимость в `pom.xml`
```
<dependency>
    <groupId>ru.capitalbank</groupId>
    <artifactId>kafka-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

или в `build.gradle`
```
dependencies {
    implementation 'ru.capitalbank:kafka-starter:0.0.1'
    }
```

## Конфигурация
Добавьте в `application.yaml` только необходимые параметры:
```
spring:
  application:
    name: kafka-starter
    enable: true

  kafka:
    bootstrap-servers: ${SPRING_KAFKA_BOOTSTRAP_SERVERS}
    topic:
      name:
        ${SPRING_KAFKA_TOPIC_NAME}
    properties:
      replication-factor: 1
      partitions: 3
      topic:
        cleanup.policy: delete
        retention.ms: 604800000
        retention.bytes: -1
        segment.bytes: 1073741824
        min.insync.replicas: 2
        max.message.bytes: 1048576
        compression.type: snappy
        flush.messages: 1000
        flush.ms: 5000
    consumer:
      group-id: ${SPRING_KAFKA_CONSUMER_GROUP_ID}
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      auto-offset-reset: earliest
      enable-auto-commit: false
      fetch-min-bytes: 50000
      fetch-max-wait-ms: 100
      max-poll-records: 100
      properties:
        session.timeout.ms: 15000
        heartbeat.interval.ms: 5000
        max.poll.interval.ms: 300000
        max.partition.fetch.bytes: 10485760
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      retries: 3
      acks: -1
      retryBackoff: 100
      topics:
        ${SPRING_KAFKA_TOPIC_NAME}
      batch-size: 16384
      buffer-memory: 33554432
      compression-type: snappy
      properties:
        linger.ms: 5
        compression.type: snappy
        max.in.flight.requests.per.connection: 5
        max.request.size: 1048576
        delivery.timeout.ms: 30000
        request.timeout.ms: 15000

  starter:
    container:
      enabled: false
    producer:
      enabled: false
    consumer:
      enabled: false
```

## Использование
После добавления зависимости и конфигурации, стартер автоматически подключает нужные бины в зависимости от наличия значений параметра enabled false/true в spring.starter...



