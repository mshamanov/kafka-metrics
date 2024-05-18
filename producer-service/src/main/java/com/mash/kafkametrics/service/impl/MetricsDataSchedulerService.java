package com.mash.kafkametrics.service.impl;

import com.mash.kafkametrics.publisher.DataPublisher;
import com.mash.kafkametrics.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Class to be run as a scheduler (with a specified fixed rate) to start publishing data when the application starts.
 *
 * @author Mikhail Shamanov
 */
@Service
@RequiredArgsConstructor
public class MetricsDataSchedulerService implements SchedulerService {
    private final DataPublisher dataPublisher;

    @Scheduled(fixedRate = 30L, initialDelay = 3L, timeUnit = TimeUnit.SECONDS)
    public void schedule() {
        this.dataPublisher.publish();
    }
}
