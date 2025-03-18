package ru.capitalbank.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
public class KafkaContainerTopic {
    private static final int PARTITIONS = 1;
    private static final short REPLICATION_FACTOR = 1;
    private static final String NAME_OF_TOPIC = "capitalbank";
    private final KafkaContainerInitializer kafkaContainerInitializer;

    public KafkaContainerTopic(KafkaContainerInitializer kafkaContainerInitializer) {
        this.kafkaContainerInitializer = kafkaContainerInitializer;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createTopic() {
        String bootstrapServers = kafkaContainerInitializer.getBootstrapServers();

        if (bootstrapServers == null || bootstrapServers.isBlank()) {
            throw new IllegalStateException("Кафка сервер не инициализирован!");
        }

        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (Admin admin = Admin.create(config)) {
            NewTopic newTopic = new NewTopic(NAME_OF_TOPIC, PARTITIONS, REPLICATION_FACTOR);
            admin.createTopics(List.of(newTopic)).all().get();
            log.info("Кафка топик успешно создан");
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Ошибка при создании топика кафки", e);
        }
    }
}
