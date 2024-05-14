package com.mash.kafkametrics.controller;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.service.sender.DataSender;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.KafkaException;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {
    private final DataSender<String, MetricsData> dataSender;

    @PostMapping
    public ResponseEntity<?> publishMetrics(@RequestBody MetricsData data) {
        try {
            CompletableFuture<SendResult<String, MetricsData>> send = this.dataSender.send(data);
            send.get();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send metrics", e);
        }
    }
}
