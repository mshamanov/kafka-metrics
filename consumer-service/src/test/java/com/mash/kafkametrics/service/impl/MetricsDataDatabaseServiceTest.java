package com.mash.kafkametrics.service.impl;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.repository.MetricsDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MetricsDataDatabaseServiceTest {
    @Mock
    MetricsDataRepository repository;

    @InjectMocks
    MetricsDataDatabaseService service;

    @Test
    void saveMetricsData_shouldSaveMetricsData() {
        MetricsData metricsData = MetricsData.builder().name("app.name").data("UP").build();
        ArgumentCaptor<MetricsData> captor = ArgumentCaptor.forClass(MetricsData.class);
        Mockito.doReturn(metricsData).when(this.repository).save(captor.capture());

        this.service.save(metricsData);

        Assertions.assertEquals(metricsData, captor.getValue());
    }

    @Test
    void saveAllMetricsData_shouldSaveAllMetricsData() {
        List<MetricsData> metricsList = List.of(
                MetricsData.builder().name("app.name").data("UP").build(),
                MetricsData.builder().name("jvm.info").data(System.getProperty("java.vm.name")).build()
        );
        ArgumentCaptor<List<MetricsData>> captor = ArgumentCaptor.captor();
        Mockito.doReturn(metricsList).when(this.repository).saveAll(captor.capture());

        this.service.saveAll(metricsList);

        Assertions.assertEquals(metricsList, captor.getValue());
    }

    @Test
    void findMetricsData_shouldFindMetricsData() {
        MetricsData metricsData = MetricsData.builder().name("app.name").data("UP").build();
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.doReturn(Optional.of(metricsData)).when(this.repository).findById(captor.capture());

        MetricsData result = this.service.findById(metricsData.getName());

        Assertions.assertEquals(metricsData.getName(), captor.getValue());
        Assertions.assertEquals(metricsData, result);
    }

    @Test
    void findAllMetricsData_shouldFindAllMetricsData() {
        List<MetricsData> metricsList = List.of(
                MetricsData.builder().name("app.name").data("UP").build(),
                MetricsData.builder().name("jvm.info").data(System.getProperty("java.vm.name")).build()
        );
        Mockito.when(this.repository.findAll()).thenReturn(metricsList);

        List<MetricsData> resultList = this.service.findAll();

        Mockito.verify(this.repository).findAll();
        Mockito.verifyNoMoreInteractions(this.repository);
        Assertions.assertEquals(metricsList, resultList);
    }
}