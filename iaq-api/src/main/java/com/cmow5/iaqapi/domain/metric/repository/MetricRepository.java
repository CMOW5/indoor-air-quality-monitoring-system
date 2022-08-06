package com.cmow5.iaqapi.domain.metric.repository;

import com.cmow5.iaqapi.domain.metric.Metric;

import java.util.List;

public interface MetricRepository {

    List<Metric> findAll();
}
