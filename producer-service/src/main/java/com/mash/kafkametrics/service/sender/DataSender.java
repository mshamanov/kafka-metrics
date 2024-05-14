package com.mash.kafkametrics.service.sender;

import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

/**
 * Interface to be used as a data sender for Apache Kafka.
 *
 * @author Mikhail Shamanov
 */
public interface DataSender<K, V> {
    CompletableFuture<SendResult<K, V>> send(V value) throws Exception;
}
