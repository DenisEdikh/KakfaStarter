package ru.capitalbank.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.capitalbank.properties.KafkaPropertiesConsumerConfig;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "spring.starter.consumer", value = "enabled", havingValue = "true")
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaPropertiesConsumerConfig.class)
public class KafkaConsumerConfig {
    private final KafkaPropertiesConsumerConfig kafkaPropertiesConsumerConfig;

    @PostConstruct
    public void init() {
        System.out.println("KAFKA_CONSUMER_CONFIG_IS_STARTED");
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactoryStarter() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertiesConsumerConfig.getBootstrapServers());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaPropertiesConsumerConfig.getConsumer().getGroupId());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaPropertiesConsumerConfig.getConsumer().getKeyDeserializer());
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaPropertiesConsumerConfig.getConsumer().getValueDeserializer());
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaPropertiesConsumerConfig.getConsumer().getAutoOffsetReset().name().toLowerCase());
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaPropertiesConsumerConfig.getConsumer().isEnableAutoCommit());
        configProps.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, kafkaPropertiesConsumerConfig.getConsumer().getFetchMinBytes());
        configProps.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, kafkaPropertiesConsumerConfig.getConsumer().getFetchMaxWaitMs());
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaPropertiesConsumerConfig.getConsumer().getMaxPollRecords());
        configProps.putAll(kafkaPropertiesConsumerConfig.getConsumer().getProperties());
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryStarter());
        return factory;
    }
}
