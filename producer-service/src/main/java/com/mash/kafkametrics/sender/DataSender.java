package com.mash.kafkametrics.sender;

import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

/**
 * Interface to be used as a data sender for Apache Kafka.
 *
 * @author Mikhail Shamanov
 */
public interface DataSender<K, V> {
    /**
     * Sends the data to Apache Kafka topics.
     *
     * @param value the data to be sent
     * @return a {@link CompletableFuture} for the {@link SendResult}.
     * @throws Exception if sending the data failed
     */
    CompletableFuture<SendResult<K, V>> send(V value) throws Exception;
}
