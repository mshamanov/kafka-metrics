package com.mash.kafkametrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Consumer service to receive metrics data from Apache Kafka and expose it via REST.
 *
 * @author Mikhail Shamanov.
 */
@SpringBootApplication
public class ConsumerServiceApplication {
    private static ApplicationContext context;

    public static void main(String[] args) {
        ConsumerServiceApplication.context = SpringApplication.run(ConsumerServiceApplication.class, args);
    }

    public static ApplicationContext getApplicationContext() {
        return ConsumerServiceApplication.context;
    }
}
