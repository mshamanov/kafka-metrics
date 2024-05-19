package com.mash.kafkametrics.listener.consumer;

import java.util.function.Consumer;

/**
 * Interface that represents a processor (handler) of incoming messages from Apache Kafka topics.
 *
 * @param <T> type of incoming messages
 * @author Mikhail Shamanov
 */
public interface ListenerConsumer<T> extends Consumer<T> {

}
