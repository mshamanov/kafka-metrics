package com.mash.kafkametrics.service;

import com.mash.kafkametrics.publisher.DataPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Class to be run as a scheduler (with specified fixed rate) to start publishing data when the application starts.
 *
 * @author Mikhail Shamanov
 */
@Service
@RequiredArgsConstructor
public class KafkaPublisherService {
    private final DataPublisher dataPublisher;

    @Scheduled(fixedRate = 30L, initialDelay = 3L, timeUnit = TimeUnit.SECONDS)
    private void scheduledPublish() {
        this.dataPublisher.publish();
    }
}
