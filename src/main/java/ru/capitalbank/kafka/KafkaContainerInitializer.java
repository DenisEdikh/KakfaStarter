package ru.capitalbank.kafka;

import lombok.SneakyThrows;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.TimeUnit;

public class KafkaContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String IMAGE = "apache/kafka:3.7.2";
    private static KafkaContainer kafkaContainer;

    static {
        kafkaContainer = new KafkaContainer(DockerImageName.parse(IMAGE));
        kafkaContainer.start();
        System.out.println("KAFKA_CONTAINER STARTED");
    }

    @Override
    @SneakyThrows
    public void initialize(ConfigurableApplicationContext context) {
        int retry = 10;

        while (!kafkaContainer.isRunning() && retry > 0) {
            TimeUnit.MILLISECONDS.sleep(500);
            retry--;
        }

        TestPropertyValues.of("spring.kafka.bootstrap-servers=%s".formatted(getBootstrapServers()))
                .applyTo(context.getEnvironment());
    }

    public String getBootstrapServers() {
        return kafkaContainer.getBootstrapServers();
    }
}
