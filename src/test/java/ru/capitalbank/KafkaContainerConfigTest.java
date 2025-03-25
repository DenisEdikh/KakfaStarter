package ru.capitalbank;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ActiveProfiles("test")
@SpringBootTest
@TestPropertySource(properties = {
        "spring.starter.container.enabled=true"
})
public class KafkaContainerConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testKafkaContainerConfigLoadsBeansCorrectly() {
        assertSoftly(softly -> {
            softly.assertThat(applicationContext.containsBean("kafkaContainerConfig")).isTrue();
            softly.assertThat(applicationContext.containsBean("kafkaTopic")).isTrue();
            softly.assertThat(applicationContext.containsBean("kafkaProducerConfig")).isFalse();
            softly.assertThat(applicationContext.containsBean("kafkaConsumerConfig")).isFalse();
        });
    }

    @Test
    void testKafkaContainerConfigIsValid() {
        assertSoftly(softly -> {
            softly.assertThat(applicationContext.getBean("kafkaTopic", NewTopic.class)
                    .name()).isEqualTo("topic-1");
            softly.assertThat(applicationContext.getBean("kafkaTopic", NewTopic.class)
                    .replicationFactor()).isEqualTo((short) 1);
        });
    }
}
