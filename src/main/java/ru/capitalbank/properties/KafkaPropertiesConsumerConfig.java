package ru.capitalbank.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaPropertiesConsumerConfig {
    private String bootstrapServers;
    private Consumer consumer;

    @Setter
    @Getter
    @ToString
    public static class Consumer {
        private String groupId;
        private Class<?> keyDeserializer;
        private Class<?> valueDeserializer;
        private TypeReset autoOffsetReset;
        private boolean enableAutoCommit;
        private int fetchMinBytes;
        private int fetchMaxWaitMs;
        private int maxPollRecords;
        private Map<String, String> properties;
    }

    public enum TypeReset {
        EARLIEST,
        LATEST,
        NONE
    }
}
