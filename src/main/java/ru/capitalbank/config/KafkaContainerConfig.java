package ru.capitalbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class KafkaContainerConfig {

    @Bean
    public KafkaContainerInitializer kafkaContainerInitializer() {
        return new KafkaContainerInitializer();
    }

    @Bean
    @DependsOn("kafkaContainerInitializer")
    public KafkaContainerTopic kafkaTopic(KafkaContainerInitializer kafkaContainerInitializer) {
        return new KafkaContainerTopic(kafkaContainerInitializer);
    }
}
