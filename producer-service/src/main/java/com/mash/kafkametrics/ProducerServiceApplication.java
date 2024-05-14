package com.mash.kafkametrics;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Producer service to collect and send metrics data over Apache Kafka.
 *
 * @author Mikhail Shamanov.
 */
@EnableScheduling
@SpringBootApplication
@AllArgsConstructor
public class ProducerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerServiceApplication.class, args);
    }
}
