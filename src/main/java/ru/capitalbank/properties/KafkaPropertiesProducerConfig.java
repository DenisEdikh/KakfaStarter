package ru.capitalbank.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaPropertiesProducerConfig {
    private Producer producer;

    @Setter
    @Getter
    @ToString
    public static class Producer {
        private Class<?> keySerializer;
        private Class<?> valueSerializer;
        private int retries;
        private int acks;
        private int retryBackoff;
        private List<String> topics;
        private int batchSize;
        private int bufferMemory;
        private String compressionType;
        private Map<String, String> properties;
    }

    public enum TypeReset {
        EARLIEST,
        LATEST,
        NONE
    }
}
