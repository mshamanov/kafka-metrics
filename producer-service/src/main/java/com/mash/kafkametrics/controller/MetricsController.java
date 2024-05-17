package com.mash.kafkametrics.controller;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.sender.DataSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Rest controller to interact with the client.
 *
 * @author Mikhail Shamanov
 */
@Validated
@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MetricsController {
    private final DataSender<String, MetricsData> dataSender;

    @PostMapping
    public ResponseEntity<?> publishMetrics(@Valid @RequestBody MetricsData data) {
        try {
            CompletableFuture<SendResult<String, MetricsData>> send = this.dataSender.send(data);
            send.get(1, TimeUnit.MINUTES);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send metrics", e);
        }
    }
}
