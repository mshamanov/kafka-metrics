package com.mash.kafkametrics.listener.consumer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mash.kafkametrics.listener.consumer.MetricsMessagesConsumer;
import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.MetricsDataService;
import com.mash.kafkametrics.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetricsDatabasePersistentConsumer implements MetricsMessagesConsumer {
    private final MetricsDataService service;

    @Override
    public void accept(List<Message<MetricsData>> metricsData) {
        Map<String, Map<String, Object>> nestedMetrics = new HashMap<>();
        Map<String, String> plainMetrics = new HashMap<>();

        metricsData.forEach(message -> {
            MetricsData payload = message.getPayload();
            try {
                String data = payload.getData();
                if (JsonUtils.isObject(data)) {
                    Map<String, Object> map = JsonUtils.readAsMap(data);
                    nestedMetrics.put(payload.getName(), map);
                } else {
                    plainMetrics.put(payload.getName(), payload.getData());
                }

                MetricsData save = this.service.save(payload);
                log.info("Metrics '{}' has been persisted", save.getName());
            } catch (JsonProcessingException e) {
                log.error("Error while converting metrics message to json", e);
            }
        });

        this.processMetricsData(nestedMetrics, plainMetrics);
    }

    private void processMetricsData(Map<String, Map<String, Object>> nestedMetrics,
                                    Map<String, String> plainMetrics) {
        CompletableFuture.runAsync(() -> {
            log.info("Processing nested metrics...: {}", nestedMetrics.size());
            log.info("Processing plain metrics...: {}", plainMetrics.size());
        });
    }
}
