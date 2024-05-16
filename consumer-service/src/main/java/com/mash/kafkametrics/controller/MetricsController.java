package com.mash.kafkametrics.controller;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.MetricsDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest controller to interact with the client.
 *
 * @author Mikhail Shamanov
 */
@Validated
@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {
    private final MetricsDataService service;

    @GetMapping
    public ResponseEntity<?> getAllMetrics() {
        List<MetricsData> metricsData = this.service.findAll();
        return ResponseEntity.ok(metricsData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMetrics(@PathVariable String id) {
        MetricsData data = this.service.findById(id);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(data);
    }
}
