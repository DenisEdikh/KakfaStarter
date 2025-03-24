package ru.capitalbank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({KafkaProducerConfig.class, KafkaConsumerConfig.class, KafkaContainerConfig.class})
@Configuration
public class KafkaConfig {
}
