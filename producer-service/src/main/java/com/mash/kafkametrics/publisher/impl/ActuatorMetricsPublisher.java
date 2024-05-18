package com.mash.kafkametrics.publisher.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.publisher.DataPublisher;
import com.mash.kafkametrics.sender.DataSender;
import com.mash.kafkametrics.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Class to be used as a metrics data publisher for sending collected metrics data over Apache Kafka.
 * It collects data from various Spring Actuator endpoints and sends it over the Apache kafka using {@link DataSender}.
 *
 * @author Mikhail Shamanov
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ActuatorMetricsPublisher implements DataPublisher {
    private final DataSender<String, MetricsData> dataSender;

    private final MetricsEndpoint metricsEndpoint;
    private final HealthEndpoint healthEndpoint;

    @Override
    public void publish() {
        try {
            for (MetricsData metricsData : this.collectMetrics()) {
                this.dataSender.send(metricsData);
            }
        } catch (Exception e) {
            log.error("Error while publishing metrics data", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Collects all metrics data in a {@link List}.
     *
     * @return a bunch of metrics data
     * @throws JsonProcessingException when fails to parse data as json
     */
    public List<MetricsData> collectMetrics() throws JsonProcessingException {
        return Stream.of(healthMetrics(), commonMetrics())
                .flatMap(List::stream)
                .toList();
    }

    /**
     * Collects metrics data on application health information from {@link HealthEndpoint}.
     *
     * @return list of metrics
     * @throws JsonProcessingException when fails to parse data as json
     */
    public List<MetricsData> healthMetrics() throws JsonProcessingException {
        return Collections.singletonList(MetricsData.builder()
                .name("health")
                .data(JsonUtils.stringify(this.healthEndpoint.health()))
                .build());
    }

    /**
     * Collects various application metrics data from {@link MetricsEndpoint}.
     *
     * @return list of metrics
     * @throws JsonProcessingException when fails to parse data as json
     */
    public List<MetricsData> commonMetrics() throws JsonProcessingException {
        List<MetricsData> resultList = new ArrayList<>();

        for (var descriptorName : this.metricsEndpoint.listNames().getNames()) {
            MetricsEndpoint.MetricDescriptor metric = this.metricsEndpoint.metric(descriptorName, null);

            MetricsData metricsData = MetricsData.builder()
                    .name(descriptorName)
                    .data(JsonUtils.stringify(metric))
                    .description(Optional.ofNullable(metric.getDescription()).orElse("")).build();

            resultList.add(metricsData);
        }

        return resultList;
    }
}
