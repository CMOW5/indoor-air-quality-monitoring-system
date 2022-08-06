package com.cmow5.iaqapi.infrastructure.metric.entity;

import com.cmow5.iaqapi.domain.metric.Metric;
import com.cmow5.iaqapi.domain.metric.MetricMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

import static com.cmow5.iaqapi.infrastructure.metric.entity.MetricEntity.METRICS_COLLECTION_NAME;

@Document(METRICS_COLLECTION_NAME)
public class MetricEntity {

    public static final String METRICS_COLLECTION_NAME = "metrics";

    @Id
    private String id;

    private String name;

    private String description;

    private String unit;

    private MetricMetadataEntity metadata;

    public MetricEntity() {
    }

    public static Metric toDomain(MetricEntity entity) {
        MetricMetadata metadata = MetricMetadataEntity.toDomain(entity.getMetadata());
        return new Metric(entity.getId(), entity.getName(), entity.getDescription(), entity.getUnit(), metadata);
    }

    public static List<Metric> toDomain(List<MetricEntity> entities) {
        return entities.stream().map(MetricEntity::toDomain).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public MetricMetadataEntity getMetadata() {
        return metadata;
    }

    public void setMetadata(MetricMetadataEntity metadata) {
        this.metadata = metadata;
    }
}
