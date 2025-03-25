package ru.capitalbank.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaPropertiesServerConfig {
    private String bootstrapServers;
}
