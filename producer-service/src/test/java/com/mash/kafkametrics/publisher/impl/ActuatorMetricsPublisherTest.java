package com.mash.kafkametrics.publisher.impl;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.sender.DataSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Status;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ActuatorMetricsPublisherTest {
    @Mock
    DataSender<String, MetricsData> dataSender;

    @Spy
    @InjectMocks
    ActuatorMetricsPublisher publisher;

    @Test
    void handlePublish_whenMetricsDataGetsCollected_thenMetricsDataGetsPublished() throws Exception {
        MetricsData appHealth = MetricsData.builder().name("app.health").data(Status.UP.toString()).build();
        MetricsData jvmInfo = MetricsData.builder().name("jvm.info").data(System.getProperty("java.vm.name")).build();
        Mockito.doReturn(List.of(appHealth, jvmInfo)).when(this.publisher).collectMetrics();

        this.publisher.publish();

        Mockito.verify(this.dataSender).send(appHealth);
        Mockito.verify(this.dataSender).send(jvmInfo);
    }

    @Test
    void handlePublish_whenCollectingMetricsDataThrowsException_thenMetricsDataIsNotPublished() throws Exception {
        Mockito.doThrow(new RuntimeException("Something went wrong")).when(this.publisher).collectMetrics();

        Assertions.assertThrows(Exception.class, () -> this.publisher.publish());

        Mockito.verifyNoInteractions(this.dataSender);
    }
}