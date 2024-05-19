package com.mash.kafkametrics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.MetricsDataService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MetricsController.class)
class MetricsControllerMvcTest {
    static final String METRICS_ENDPOINT = "/api/v1/metrics";

    @MockBean
    MetricsDataService service;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleAllGetMetrics_whenAllMetricsDataRequested_thenReturnsAllMetricsData() throws Exception {
        MetricsData healthMetrics = MetricsData.builder()
                .name("app.name")
                .data("UP")
                .build();

        MetricsData jvmInfoMetrics = MetricsData.builder()
                .name("jvm.info")
                .data(System.getProperty("java.vm.name"))
                .description("Java Virtual Machine Information")
                .build();

        MetricsData nestedMetrics = MetricsData.builder()
                .name("custom.service")
                .data("{\"status\":\"UP\",\"details\":{\"uptime\":1906}}")
                .description("custom service metrics information")
                .build();

        List<MetricsData> metricsList = List.of(healthMetrics, jvmInfoMetrics, nestedMetrics);

        Mockito.when(this.service.findAll()).thenReturn(metricsList);

        this.mockMvc.perform(get(MetricsControllerMvcTest.METRICS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$").isArray(),
                        jsonPath("$", Matchers.hasSize(3)),
                        jsonPath("$[0].name").value(healthMetrics.getName()),
                        jsonPath("$[0].data").value(healthMetrics.getData()),
                        jsonPath("$[1].name").value(jvmInfoMetrics.getName()),
                        jsonPath("$[1].data").value(jvmInfoMetrics.getData()),
                        jsonPath("$[2].name").value(nestedMetrics.getName()),
                        jsonPath("$[2].data").hasJsonPath(),
                        jsonPath("$[2].data.status").value("UP"),
                        jsonPath("$[2].data.details.uptime").value(1906)
                );

        Mockito.verify(this.service).findAll();
        Mockito.verifyNoMoreInteractions(this.service);
    }

    @Test
    void handleGetMetricsById_whenMetricsDataRequestedById_thenReturnsRequestedMetricsData() throws Exception {
        MetricsData nestedMetrics = MetricsData.builder()
                .name("custom.service")
                .data("{\"status\":\"UP\",\"details\":{\"uptime\":1906}}")
                .description("custom service metrics information")
                .build();

        Mockito.when(this.service.findById(nestedMetrics.getName())).thenReturn(nestedMetrics);

        this.mockMvc.perform(get(MetricsControllerMvcTest.METRICS_ENDPOINT + "/{id}", nestedMetrics.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.name").value(nestedMetrics.getName()),
                        jsonPath("$.data").hasJsonPath(),
                        jsonPath("$.data.status").value("UP"),
                        jsonPath("$.data.details.uptime").value(1906),
                        jsonPath("$.description").value(nestedMetrics.getDescription())
                );

        Mockito.verify(this.service).findById(nestedMetrics.getName());
        Mockito.verifyNoMoreInteractions(this.service);
    }

    @Test
    void handleGetMetricsById_whenMetricsDataNotFound_thenReturnsNotFoundError() throws Exception {
        MetricsData nestedMetrics = MetricsData.builder()
                .name("custom.service")
                .data("{\"status\":\"UP\",\"details\":{\"uptime\":1906}}")
                .description("custom service metrics information")
                .build();

        Mockito.when(this.service.findById(nestedMetrics.getName())).thenReturn(null);

        this.mockMvc.perform(get(MetricsControllerMvcTest.METRICS_ENDPOINT + "/{id}", nestedMetrics.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.title").value(HttpStatus.NOT_FOUND.getReasonPhrase()),
                        jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()),
                        jsonPath("$.detail").value(Matchers.containsString(nestedMetrics.getName() + " not found"))
                );

        Mockito.verify(this.service).findById(nestedMetrics.getName());
        Mockito.verifyNoMoreInteractions(this.service);
    }
}