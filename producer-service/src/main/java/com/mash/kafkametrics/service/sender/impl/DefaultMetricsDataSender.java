package com.mash.kafkametrics.service.sender.impl;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.sender.DataSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Class to be used as a default metrics data sender {@link MetricsData} for Apache Kafka.
 *
 * @author Mikhail Shamanov
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultMetricsDataSender implements DataSender<String, MetricsData> {
    private final NewTopic topic;
    private final KafkaTemplate<String, MetricsData> kafkaTemplate;

    @Override
    public CompletableFuture<SendResult<String, MetricsData>> send(MetricsData value) {
        try {
            log.info("Publishing metrics data: {}", value);
            return this.kafkaTemplate.send(this.topic.name(), value)
                    .whenComplete((res, err) -> {
                        if (err == null) {
                            log.info("Metrics data: '{}' has been published, offset: {}",
                                    value.getName(),
                                    res.getRecordMetadata().offset());
                        } else {
                            log.error("Metrics data: '{}' failed to get published", value.getName(), err);
                        }
                    });
        } catch (Exception e) {
            log.error("Error while trying to publish metrics data: {}", value, e);
            throw e;
        }
    }
}
