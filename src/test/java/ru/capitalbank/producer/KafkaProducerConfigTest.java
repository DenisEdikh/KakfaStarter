package ru.capitalbank.producer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.capitalbank.config.KafkaConfig;
import ru.capitalbank.config.KafkaContainerConfig;
import ru.capitalbank.config.KafkaProducerConfig;
import ru.capitalbank.properties.KafkaPropertiesProducerConfig;

import static org.assertj.core.api.Assertions.assertThat;

//@ActiveProfiles("test")
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@ContextConfiguration(classes = {
        KafkaConfig.class,
        KafkaContainerConfig.class,
        KafkaProducerConfig.class,
        KafkaPropertiesProducerConfig.class
})
public class KafkaProducerConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
//            .withPropertyValues("spring.starter.producer.enabled=true")
            .withUserConfiguration(KafkaProducerConfig.class);

    @Test
    void testKafkaProducerConfigLoadsBeansCorrectly() {
        contextRunner.withBean(KafkaPropertiesProducerConfig.class)
                .run(context -> {
                    assertThat(context).hasBean("producerFactoryStarter");
                    assertThat(context).hasBean("kafkaTemplateStarter");
                    assertThat(context.getBean("kafkaTemplateStarter")).isNotNull();
                });
    }
}