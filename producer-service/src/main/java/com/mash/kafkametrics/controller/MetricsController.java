package com.mash.kafkametrics.controller;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.sender.DataSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Rest controller to interact with the client.
 *
 * @author Mikhail Shamanov
 */
@Validated
@RestController
@RequestMapping(path = "/api/v1/metrics",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MetricsController {
    private final DataSender<String, MetricsData> dataSender;

    @PostMapping
    public ResponseEntity<?> publishMetrics(@Valid @RequestBody MetricsData data) {
        try {
            CompletableFuture<SendResult<String, MetricsData>> send = this.dataSender.send(data);
            send.get(1, TimeUnit.MINUTES);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send metrics", e);
        }
    }
}
