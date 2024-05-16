package com.mash.kafkametrics.repository;

import com.mash.kafkametrics.model.MetricsData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricsDataRepository extends JpaRepository<MetricsData, String> {
}
