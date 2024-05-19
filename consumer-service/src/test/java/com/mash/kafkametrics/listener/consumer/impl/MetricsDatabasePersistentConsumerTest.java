package com.mash.kafkametrics.listener.consumer.impl;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.impl.MetricsDataDatabaseService;
import com.mash.kafkametrics.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MetricsDatabasePersistentConsumerTest {
    @Mock
    MetricsDataDatabaseService service;

    @InjectMocks
    MetricsDatabasePersistentConsumer consumer;

    @Test
    void whenAccepted_shouldCallSaveMetricsDataOfDatabaseService() {
        MessageHeaderAccessor accessor = new MessageHeaderAccessor();
        accessor.setHeader("foo", "bar");
        MessageHeaders headers = accessor.getMessageHeaders();

        MetricsData healthMetrics = MetricsData.builder()
                .name("app.name")
                .data("UP")
                .build();

        MetricsData jvmInfoMetrics = MetricsData.builder()
                .name("jvm.info")
                .data(System.getProperty("java.vm.name"))
                .build();

        List<MetricsData> metricsList = List.of(healthMetrics, jvmInfoMetrics);

        List<Message<MetricsData>> messages = metricsList.stream()
                .map(m -> MessageBuilder.createMessage(m, headers))
                .toList();

        ArgumentCaptor<MetricsData> captor = ArgumentCaptor.forClass(MetricsData.class);
        Mockito.doReturn(healthMetrics, jvmInfoMetrics).when(this.service).save(captor.capture());

        try (MockedStatic<JsonUtils> mockedStatic = Mockito.mockStatic(JsonUtils.class)) {
            mockedStatic.when(JsonUtils::getObjectMapper).thenReturn(JacksonUtils.enhancedObjectMapper());
            this.consumer.accept(messages);
        }

        Assertions.assertEquals(metricsList, captor.getAllValues());
    }
}