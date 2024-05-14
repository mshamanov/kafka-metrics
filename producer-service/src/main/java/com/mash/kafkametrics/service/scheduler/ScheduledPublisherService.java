package com.mash.kafkametrics.service.scheduler;

import com.mash.kafkametrics.service.publisher.DataPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class to be run as a scheduler (with specified fixed rate) to start publishing data when the application starts.
 *
 * @author Mikhail Shamanov
 */
@Service
@RequiredArgsConstructor
public class ScheduledPublisherService {
    private final DataPublisher dataPublisher;

    //    @Scheduled(fixedRate = 60L, initialDelay = 3L, timeUnit = TimeUnit.SECONDS)
    private void scheduledPublish() {
        this.dataPublisher.publish();
    }
}
