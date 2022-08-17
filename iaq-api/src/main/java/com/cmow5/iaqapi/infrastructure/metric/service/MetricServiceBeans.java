package com.cmow5.iaqapi.infrastructure.metric.service;

import com.cmow5.iaqapi.domain.metric.repository.MetricRepository;
import com.cmow5.iaqapi.domain.metric.service.MetricService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricServiceBeans {

    @Bean
    public MetricService metricService(MetricRepository metricRepository) {
        return new MetricService(metricRepository);
    }
}
