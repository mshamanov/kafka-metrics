package com.mash.kafkametrics.listener.consumer;

import com.mash.kafkametrics.model.MetricsData;
import org.springframework.messaging.Message;

import java.util.List;

/**
 * Interface that represents a processor of the metrics data {@link MetricsData} consumed from Apache Kafka topics.
 *
 * @author Mikhail Shamanov
 */
public interface MetricsMessagesConsumer extends ListenerConsumer<List<Message<MetricsData>>> {
}
