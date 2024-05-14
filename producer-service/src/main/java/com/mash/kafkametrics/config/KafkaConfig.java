package com.mash.kafkametrics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mash.kafkametrics.model.MetricsData;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Slf4j
@Configuration
public class KafkaConfig {
    public final String topicName;

    public KafkaConfig(@Value("${producer-service.kafka.topic}") String topicName) {
        this.topicName = topicName;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ProducerFactory<String, MetricsData> producerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties(null);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:29092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        DefaultKafkaProducerFactory<String, MetricsData> factory = new DefaultKafkaProducerFactory<>(props);
        factory.setValueSerializer(new JsonSerializer<>(mapper));
        return factory;
    }

    @Bean
    public KafkaTemplate<String, MetricsData> kafkaTemplate(ProducerFactory<String, MetricsData> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(this.topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }
}