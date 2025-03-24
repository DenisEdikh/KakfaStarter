package ru.capitalbank.config;

import jakarta.annotation.PostConstruct;
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

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "spring.starter.producer", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaPropertiesProducerConfig.class)
public class KafkaProducerConfig<T> {
    private final KafkaPropertiesProducerConfig kafkaPropertiesProducerConfig;

    @PostConstruct
    public void init() {
        System.out.println("KAFKA_PRODUCER_CONFIG_IS_STARTED");
    }

    @Bean
    public ProducerFactory<String, Object> producerFactoryStarter() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaPropertiesProducerConfig.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaPropertiesProducerConfig.getProducer().getKeySerializer());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                kafkaPropertiesProducerConfig.getProducer().getValueSerializer());
//        configProps.put(ProducerConfig.RETRIES_CONFIG,
//                String.valueOf(kafkaPropertiesProducerConfig.getProducer().getRetries()));
//        configProps.put(ProducerConfig.ACKS_CONFIG,
//                String.valueOf(kafkaPropertiesProducerConfig.getProducer().getAcks()));
//        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,
//                String.valueOf(kafkaPropertiesProducerConfig.getProducer().getRetryBackoff()));
//        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG,
//                String.valueOf(kafkaPropertiesProducerConfig.getProducer().getBatchSize()));
//        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG,
//                String.valueOf(kafkaPropertiesProducerConfig.getProducer().getBufferMemory()));
//        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,
//                String.valueOf(kafkaPropertiesProducerConfig.getProducer().getCompressionType()));

        kafkaPropertiesProducerConfig.getProducer().getValueSerializer());
        configProps.put(ProducerConfig.RETRIES_CONFIG, kafkaPropertiesProducerConfig.getProducer().getRetries());
        configProps.put(ProducerConfig.ACKS_CONFIG, kafkaPropertiesProducerConfig.getProducer().getAcks());
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, kafkaPropertiesProducerConfig.getProducer().getRetryBackoff());
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaPropertiesProducerConfig.getProducer().getBatchSize());
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaPropertiesProducerConfig.getProducer().getBufferMemory());
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaPropertiesProducerConfig.getProducer().getCompressionType());

        configProps.putAll(kafkaPropertiesProducerConfig.getProducer().getProperties());
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplateStarter() {
        return new KafkaTemplate<>(producerFactoryStarter());
    }
}
