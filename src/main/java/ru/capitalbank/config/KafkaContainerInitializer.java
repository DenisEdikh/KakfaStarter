package ru.capitalbank.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class KafkaContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String IMAGE = "apache/kafka:3.7.2";
    private static KafkaContainer kafkaContainer;

    static {
        kafkaContainer = new KafkaContainer(DockerImageName.parse(IMAGE));
        kafkaContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        kafkaContainer.start();
        TestPropertyValues.of("spring.kafka.bootstrap-servers=" + getBootstrapServers())
                .applyTo(context.getEnvironment());
    }

    public String getBootstrapServers() {
        return kafkaContainer.getBootstrapServers();
    }
}

