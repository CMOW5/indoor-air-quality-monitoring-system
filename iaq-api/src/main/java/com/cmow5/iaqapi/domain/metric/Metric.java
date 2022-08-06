package com.cmow5.iaqapi.domain.metric;

public class Metric {

    private final String id;

    private final String name;

    private final String description;

    private final String unit;

    private final MetricMetadata metadata;

    public Metric(String id, String name, String description, String unit, MetricMetadata metadata) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    public MetricMetadata getMetadata() {
        return metadata;
    }
}
