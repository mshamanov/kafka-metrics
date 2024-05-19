package com.mash.kafkametrics.controller;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.MetricsDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Rest controller to interact with the client.
 *
 * @author Mikhail Shamanov
 */
@Validated
@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MetricsController {
    private final MetricsDataService service;

    @GetMapping
    public ResponseEntity<?> getAllMetrics() {
        List<MetricsData> metricsData = this.service.findAll();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(metricsData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMetrics(@PathVariable String id) {
        MetricsData data = this.service.findById(id);

        if (data == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Metrics with id " + id + " not found");
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data);
    }
}
