package ru.capitalbank;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ActiveProfiles("test")
@SpringBootTest
@TestPropertySource(properties = {
        "spring.starter.producer.enabled=true"
})
public class KafkaProducerConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testKafkaProducerConfigLoadsBeansCorrectly() {
        assertThat(applicationContext.containsBean("kafkaTemplate")).isTrue();
    }

    @Test
    void testKafkaProducerConfigIsValid() {
        Map<String, Object> producerFactory = applicationContext
                .getBean("kafkaTemplate", KafkaTemplate.class)
                .getProducerFactory()
                .getConfigurationProperties();

        assertSoftly(softly -> {
            softly.assertThat(producerFactory.get(ProducerConfig.RETRIES_CONFIG)).isEqualTo(3);
            softly.assertThat(producerFactory.get("max.request.size")).isEqualTo("1048576");
        });
    }
}