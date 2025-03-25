package ru.capitalbank;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.capitalbank.config.KafkaConsumerConfig;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ActiveProfiles("test")
@SpringBootTest
@TestPropertySource(properties = {
        "spring.starter.consumer.enabled=true"
})
public class KafkaConsumerConfigTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;

    @Test
    void testKafkaConsumerConfigLoadsBeansCorrectly() {
        assertThat(applicationContext.containsBean("kafkaConsumerConfig")).isTrue();
    }

    @Test
    void testKafkaConsumerConfigIsValid(){
        Map<String, Object> kafkaListenerContainerFactory = applicationContext
                .getBean("kafkaListenerContainerFactory", ConcurrentKafkaListenerContainerFactory.class)
                .getConsumerFactory()
                .getConfigurationProperties();

        assertSoftly(softly -> {
            softly.assertThat(kafkaListenerContainerFactory.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)).isEqualTo("earliest");
            softly.assertThat(kafkaListenerContainerFactory.get("session.timeout.ms")).isEqualTo("15000");
        });
    }
}
