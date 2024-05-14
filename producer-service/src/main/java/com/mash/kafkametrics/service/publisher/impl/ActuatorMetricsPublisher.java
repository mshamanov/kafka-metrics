package com.mash.kafkametrics.service.publisher.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.publisher.DataPublisher;
import com.mash.kafkametrics.service.sender.DataSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Service;

/**
 * Class to be used as a metrics data publisher for Apache Kafka.
 * It collects data from various Spring Actuator endpoints and sends it over the Apache kafka using {@link DataSender}.
 *
 * @author Mikhail Shamanov
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ActuatorMetricsPublisher implements DataPublisher {
    private final DataSender<String, MetricsData> dataSender;
    private final ObjectMapper objectMapper;

    private final MetricsEndpoint metricsEndpoint;
    private final HealthEndpoint healthEndpoint;

    @Override
    public void publish() {
        try {
            this.dataSender.send(new MetricsData("health", this.toJson(this.healthEndpoint.health())));

            for (var descriptorName : this.metricsEndpoint.listNames().getNames()) {
                MetricsEndpoint.MetricDescriptor metric = this.metricsEndpoint.metric(descriptorName, null);
                this.dataSender.send(new MetricsData(descriptorName, this.toJson(metric)));
            }
        } catch (Exception e) {
            log.error("Error while publishing metric", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts an object to json as string
     *
     * @param value any object
     * @return object as string
     * @throws JsonProcessingException in case it failed to convert an object to a json
     */
    private String toJson(Object value) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(value);
    }
}
