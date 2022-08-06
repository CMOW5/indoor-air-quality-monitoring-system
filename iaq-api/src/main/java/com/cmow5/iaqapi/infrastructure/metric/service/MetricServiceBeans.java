package com.cmow5.iaqapi.infrastructure.metric.service;

import com.cmow5.iaqapi.domain.metric.repository.MetricRepository;
import com.cmow5.iaqapi.domain.metric.service.MetricService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cmow5.iaqapi.infrastructure.metric.repository.MetricMongoRepository.METRIC_MONGO_REPOSITORY_BEAN;

@Configuration
public class MetricServiceBeans {

    @Bean
    public MetricService metricService(@Qualifier(METRIC_MONGO_REPOSITORY_BEAN) MetricRepository metricRepository) {
        return new MetricService(metricRepository);
    }
}
