package ru.capitalbank.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import ru.capitalbank.kafka.KafkaContainerInitializer;
import ru.capitalbank.properties.KafkaPropertiesTopicConfig;

@Configuration
@ConditionalOnProperty(prefix = "spring.starter.container", value = "enabled", havingValue = "true")
@EnableConfigurationProperties(KafkaPropertiesTopicConfig.class)
public class KafkaContainerConfig {

    @Bean
    public KafkaContainerInitializer kafkaContainerInitializerStarter() {
        return new KafkaContainerInitializer();
    }

    @Bean
    @DependsOn("kafkaContainerInitializerStarter")
    public NewTopic kafkaTopic(KafkaPropertiesTopicConfig prop) {
        return new NewTopic(
                prop.getTopic().getName(),
                prop.getProperties().getPartitions(),
                prop.getProperties().getReplicationFactor()
        ).configs(prop.getProperties().getProps());
    }
}
