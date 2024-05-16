package com.mash.kafkametrics.listener.consumer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mash.kafkametrics.listener.consumer.MetricsMessagesConsumer;
import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.MetricsDataService;
import com.mash.kafkametrics.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class MetricsDatabasePersistentConsumer implements MetricsMessagesConsumer {
    private final MetricsDataService service;

    @Override
    public void accept(List<Message<MetricsData>> metricsData) {
        Map<String, Map<String, Object>> metricsMap = new HashMap<>();

        metricsData.forEach(message -> {
            MetricsData payload = message.getPayload();
            try {
                Map<String, Object> map = JsonUtils.readAsMap(payload.getData());
                metricsMap.put(payload.getName(), map);
                MetricsData save = this.service.save(payload);
                log.info("Metrics '{}' has been persisted", save.getName());
            } catch (JsonProcessingException e) {
                log.error("Error while converting metrics message to json", e);
            }
        });

        CompletableFuture.runAsync(() -> {
            log.info("Processing metrics data...: {}", metricsMap.size());
        });
    }
}
