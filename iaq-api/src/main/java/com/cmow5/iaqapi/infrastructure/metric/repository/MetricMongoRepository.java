package com.cmow5.iaqapi.infrastructure.metric.repository;

import com.cmow5.iaqapi.domain.metric.Metric;
import com.cmow5.iaqapi.domain.metric.repository.MetricRepository;
import com.cmow5.iaqapi.infrastructure.metric.entity.MetricEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.metric.repository.MetricMongoRepository.METRIC_MONGO_REPOSITORY_BEAN;

@Repository(METRIC_MONGO_REPOSITORY_BEAN)
public class MetricMongoRepository implements MetricRepository {

    public static final String METRIC_MONGO_REPOSITORY_BEAN = "metricMongoRepository";

    private final MongoOperations mongodbTemplate;

    public MetricMongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    @Override
    public List<Metric> findAll() {
        List<MetricEntity> entities = this.mongodbTemplate.findAll(MetricEntity.class);
        return MetricEntity.toDomain(entities);
    }
}
