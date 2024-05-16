package com.mash.kafkametrics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class to store custom Apache Kafka properties loaded from application properties.
 *
 * @author Mikhail Shamanov
 */
@Data
@Configuration
@ConfigurationProperties("producer-service.kafka")
public class KafkaCustomProperties {
    private String topic;
}
