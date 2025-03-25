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
public class KafkaPropertiesTopicConfig {
    private Topic topic;
    private Property properties;

    @Getter
    @Setter
    @ToString
    public static class Topic {
        private String name;
    }

    @Getter
    @Setter
    @ToString
    public static class Property {
        private short replicationFactor;
        private int partitions;
        private Map<String, String> props;
    }
}
