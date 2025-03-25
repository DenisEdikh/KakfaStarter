package ru.capitalbank.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.capitalbank.properties.KafkaPropertiesProducerConfig;
import ru.capitalbank.properties.KafkaPropertiesServerConfig;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "spring.starter.producer", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
@EnableConfigurationProperties({KafkaPropertiesProducerConfig.class, KafkaPropertiesServerConfig.class})
public class KafkaProducerConfig<T> {
    private final KafkaPropertiesProducerConfig kafkaPropertiesProducerConfig;
    private final KafkaPropertiesServerConfig kafkaPropertiesServerConfig;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaPropertiesServerConfig.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getKeySerializer());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getValueSerializer());
        configProps.put(ProducerConfig.RETRIES_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getRetries());
        configProps.put(ProducerConfig.ACKS_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getAcks());
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getRetryBackoff());
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getBatchSize());
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getBufferMemory());
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getCompressionType());

        configProps.putAll(kafkaPropertiesProducerConfig.getProducer().getProperties());

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
