package com.mash.kafkametrics.listener;

import com.mash.kafkametrics.listener.consumer.MetricsMessagesConsumer;
import com.mash.kafkametrics.model.MetricsData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Listener to poll metrics data {@link MetricsData} from related Apache Kafka topic.
 *
 * @author Mikhail Shamanov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMetricsListener {
    private final MetricsMessagesConsumer consumerStrategy;

    @KafkaListener(
            topics = "${consumer-service.kafka.topic}",
            containerFactory = "listenerContainerFactory")
    public void listen(@Payload List<Message<MetricsData>> values) {
        this.consumerStrategy.accept(values);
    }
}
