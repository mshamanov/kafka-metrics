package com.mash.kafkametrics.listener.consumer.impl;

import com.mash.kafkametrics.listener.consumer.MetricsMessagesConsumer;
import com.mash.kafkametrics.model.MetricsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MetricsLoggerConsumer implements MetricsMessagesConsumer {
    @Override
    public void accept(List<Message<MetricsData>> metricsData) {
        metricsData.forEach(data -> log.info("Metrics data received: {}", data.getHeaders()
                .get(KafkaHeaders.RECEIVED_KEY)));
    }
}
