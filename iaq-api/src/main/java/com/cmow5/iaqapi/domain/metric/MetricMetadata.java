package com.cmow5.iaqapi.domain.metric;

public class MetricMetadata {

    private final String color;

    private final String icon;

    private final int min;

    public MetricMetadata(String color, String icon, int min) {
        this.color = color;
        this.icon = icon;
        this.min = min;
    }

    public String getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }

    public int getMin() {
        return min;
    }
}
