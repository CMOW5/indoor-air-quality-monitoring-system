package com.cmow5.iaqapi.infrastructure.general.web.dto;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class SensorDataPointDto <T> {
    private Instant timestamp;

    private T value;

    public SensorDataPointDto() {}

    public SensorDataPointDto(Instant timestamp, T value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getTimestamp() {
        return DateTimeFormatter.ISO_INSTANT.format(timestamp);
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
