package ru.capitalbank.kafka;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import ru.capitalbank.properties.KafkaPropertiesTopicConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class KafkaTopic {
    private final KafkaPropertiesTopicConfig kafkaPropertiesTopicConfig;
    @Autowired
    private Environment env;

    @PostConstruct
    public void createTopic() {
        final String bootstrapServers = kafkaPropertiesTopicConfig.getBootstrapServers();
        // TODO удалить sout
        System.out.println(bootstrapServers.toUpperCase());

        System.out.println(env.getProperty("spring.kafka.bootstrap-servers"));

        if (bootstrapServers == null || bootstrapServers.isBlank()) {
            throw new IllegalStateException("Кафка сервер не инициализирован!");
        }

        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (Admin admin = Admin.create(config)) {
            final List<NewTopic> lists = new ArrayList<>();
            for (String topicName : kafkaPropertiesTopicConfig.getTopic().getNames()) {
                lists.add(new NewTopic(
                        topicName,
                        kafkaPropertiesTopicConfig.getProperties().getPartitions(),
                        kafkaPropertiesTopicConfig.getProperties().getReplicationFactor()
                ));
            }
            admin.createTopics(lists).all().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Ошибка при создании топика кафки", e);
        }
    }
}
