package com.mash.kafkametrics.service.impl;

import com.mash.kafkametrics.model.MetricsData;
import com.mash.kafkametrics.repository.MetricsDataRepository;
import com.mash.kafkametrics.service.MetricsDataService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to interact with the database repository.
 *
 * @author Mikhail Shamanov
 */
@Service
@AllArgsConstructor
public class MetricsDataDatabaseService implements MetricsDataService {
    private final MetricsDataRepository repository;

    @Transactional
    @Override
    public List<MetricsData> saveAll(Iterable<MetricsData> data) {
        return this.repository.saveAll(data);
    }

    @Transactional
    @Override
    public MetricsData save(MetricsData data) {
        return this.repository.save(data);
    }

    @Override
    public List<MetricsData> findAll() {
        return this.repository.findAll();
    }

    @Override
    public MetricsData findById(String id) {
        return this.repository.findById(id).orElse(null);
    }
}
