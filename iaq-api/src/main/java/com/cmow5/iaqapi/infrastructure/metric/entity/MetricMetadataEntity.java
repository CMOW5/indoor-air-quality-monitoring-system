package com.cmow5.iaqapi.infrastructure.metric.entity;

import com.cmow5.iaqapi.domain.metric.MetricMetadata;

public class MetricMetadataEntity {

    private String color;

    private String icon;

    private int min;

    private MetricMetadataEntity() {}

    public static MetricMetadata toDomain(MetricMetadataEntity metadataEntity) {
        return new MetricMetadata(metadataEntity.getColor(), metadataEntity.getIcon(), metadataEntity.getMin());
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
