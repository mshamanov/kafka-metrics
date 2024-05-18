package com.mash.kafkametrics.service;

import com.mash.kafkametrics.publisher.DataPublisher;
import com.mash.kafkametrics.service.impl.MetricsDataSchedulerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaPublisherServiceTest {

    @Mock
    DataPublisher dataPublisher;

    @InjectMocks
    MetricsDataSchedulerService service;

    @Test
    void shouldCallPublishMethod() {
        this.service.schedule();

        Mockito.verify(this.dataPublisher).publish();
    }
}