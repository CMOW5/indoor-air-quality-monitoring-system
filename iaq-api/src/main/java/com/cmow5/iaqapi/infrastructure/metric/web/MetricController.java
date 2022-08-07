package com.cmow5.iaqapi.infrastructure.metric.web;

import com.cmow5.iaqapi.domain.metric.Metric;
import com.cmow5.iaqapi.domain.metric.service.MetricService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping("/metrics")
    public List<Metric> findAllMetrics() {
        return this.metricService.findAll();
    }
}
