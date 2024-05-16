package com.mash.kafkametrics.listener.consumer;

import com.mash.kafkametrics.model.MetricsData;
import org.springframework.messaging.Message;

import java.util.List;

public interface MetricsMessagesConsumer extends ListenerConsumer<List<Message<MetricsData>>> {
}
