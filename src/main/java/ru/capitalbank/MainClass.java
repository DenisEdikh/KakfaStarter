package ru.capitalbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import ru.capitalbank.kafka.KafkaContainerInitializer;

@SpringBootApplication
public class MainClass {
    public static void main(String[] args) {
//        SpringApplication.run(MainClass.class, args);
        SpringApplication app = new SpringApplication(MainClass.class);
        app.addInitializers(new KafkaContainerInitializer());
        app.run(args);
    }
}
