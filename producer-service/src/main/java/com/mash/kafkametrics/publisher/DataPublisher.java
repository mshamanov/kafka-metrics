package com.mash.kafkametrics.publisher;

import com.mash.kafkametrics.sender.DataSender;

/**
 * Interface to be used as a data publisher for sending the data over Apache Kafka.
 * It involves performing some additional actions such as preparing data or collecting data before directly sending it
 * via {@link DataSender} to Apache Kafka topics.
 *
 * @author Mikhail Shamanov
 */
public interface DataPublisher {
    void publish();
}
