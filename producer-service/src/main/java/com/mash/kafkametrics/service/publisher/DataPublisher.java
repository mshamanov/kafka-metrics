package com.mash.kafkametrics.service.publisher;

import com.mash.kafkametrics.service.sender.DataSender;

/**
 * Interface to be used as a data publisher for Apache Kafka.
 * It performs some actions such as preparing data or collecting data including sending data via {@link DataSender}
 * to Apache Kafka topics.
 *
 * @author Mikhail Shamanov
 */
public interface DataPublisher {
    void publish();
}
