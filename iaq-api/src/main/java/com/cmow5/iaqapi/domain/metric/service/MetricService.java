package com.cmow5.iaqapi.domain.metric.service;

import com.cmow5.iaqapi.domain.metric.Metric;
import com.cmow5.iaqapi.domain.metric.repository.MetricRepository;

import java.util.List;

public class MetricService {

    private final MetricRepository metricRepository;

    public MetricService(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public List<Metric> findAll() {
        return this.metricRepository.findAll();
    }
}
