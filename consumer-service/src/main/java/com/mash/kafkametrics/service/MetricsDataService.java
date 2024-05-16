package com.mash.kafkametrics.service;

import com.mash.kafkametrics.model.MetricsData;

import java.util.List;

/**
 * Interface to interact with the repository.
 *
 * @author Mikhail Shamanov
 */
public interface MetricsDataService {
    List<MetricsData> saveAll(Iterable<MetricsData> data);

    MetricsData save(MetricsData data);

    List<MetricsData> findAll();

    MetricsData findById(String id);
}
