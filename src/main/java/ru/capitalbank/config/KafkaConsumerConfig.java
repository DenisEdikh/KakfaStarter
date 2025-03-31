package ru.capitalbank.config;

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
import ru.capitalbank.properties.KafkaPropertiesServerConfig;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "spring.starter.consumer", value = "enabled", havingValue = "true")
@RequiredArgsConstructor
@EnableConfigurationProperties({KafkaPropertiesConsumerConfig.class, KafkaPropertiesServerConfig.class})
public class KafkaConsumerConfig {
    private final KafkaPropertiesConsumerConfig kafkaPropertiesConsumerConfig;
    private final KafkaPropertiesServerConfig kafkaPropertiesServerConfig;

    @Bean
    public ConsumerFactory<String, String> consumerFactoryStarter() {
        Map<String, Object> configProps = new HashMap<>();
        KafkaPropertiesConsumerConfig.Consumer consumer = kafkaPropertiesConsumerConfig.getConsumer();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertiesServerConfig.getBootstrapServers());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer.getKeyDeserializer());
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumer.getValueDeserializer());
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.getAutoOffsetReset().name().toLowerCase());
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumer.isEnableAutoCommit());
        configProps.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, consumer.getFetchMinBytes());
        configProps.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, consumer.getFetchMaxWaitMs());
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumer.getMaxPollRecords());

        configProps.putAll(consumer.getProperties());

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryStarter());
        return factory;
    }
}
