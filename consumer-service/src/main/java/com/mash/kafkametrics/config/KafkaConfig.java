package com.mash.kafkametrics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mash.kafkametrics.model.MetricsData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

/**
 * Class to configure Apache Kafka consumer.
 *
 * @author Mikahil Shamanov
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    public final KafkaCustomProperties kafkaCustomProperties;

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ConsumerFactory<String, MetricsData> consumerFactory(KafkaProperties kafkaProperties, ObjectMapper mapper) {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties(null);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS,
                "com.mash.kafkametrics.model.MetricsData:com.mash.kafkametrics.model.MetricsData");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 300);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10_000);

        DefaultKafkaConsumerFactory<String, MetricsData> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(props);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(mapper));

        return kafkaConsumerFactory;
    }

    @Bean("listenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MetricsData>>
    listenerContainerFactory(ConsumerFactory<String, MetricsData> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, MetricsData>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);

        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor("k-consumer-");
        factory.getContainerProperties().setListenerTaskExecutor(simpleAsyncTaskExecutor);

        return factory;
    }
}