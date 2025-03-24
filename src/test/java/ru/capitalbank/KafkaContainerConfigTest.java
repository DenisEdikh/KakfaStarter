package ru.capitalbank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.capitalbank.config.KafkaContainerConfig;
import ru.capitalbank.kafka.KafkaContainerInitializer;
import ru.capitalbank.kafka.KafkaTopic;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
public class KafkaContainerConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
//            .withPropertyValues("spring.starter.container.enabled=true")
            .withUserConfiguration(KafkaContainerConfig.class)
            .withUserConfiguration(KafkaTopic.class);

    @Test
    public void testKafkaContainerConfigLoadsBeansCorrectly() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(KafkaContainerInitializer.class);
            assertThat(context).hasSingleBean(KafkaTopic.class);
            assertThat(context.getBean(KafkaTopic.class)).isNotNull();
        });
    }
}
