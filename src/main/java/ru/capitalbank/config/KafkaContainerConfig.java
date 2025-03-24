package ru.capitalbank.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import ru.capitalbank.kafka.KafkaContainerInitializer;
import ru.capitalbank.kafka.KafkaTopic;
import ru.capitalbank.properties.KafkaPropertiesTopicConfig;

@Configuration
@ConditionalOnProperty(prefix = "spring.starter.container", value = "enabled", havingValue = "true")
@EnableConfigurationProperties(KafkaPropertiesTopicConfig.class)
public class KafkaContainerConfig {

    @PostConstruct
    public void init() {
        System.out.println("KAFKA_CONTAINER_CONFIG_IS_STARTED");
    }

    @Bean
    public KafkaContainerInitializer kafkaContainerInitializerStarter() {
        return new KafkaContainerInitializer();
    }

    @Bean
    @DependsOn("kafkaContainerInitializerStarter")
    public KafkaTopic kafkaTopicStarter(KafkaPropertiesTopicConfig kafkaPropertiesTopicConfig) {
        return new KafkaTopic(kafkaPropertiesTopicConfig);
    }
}
