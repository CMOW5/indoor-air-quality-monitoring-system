package com.cmow5.iaqapi.domain;

import java.time.Instant;

public interface SensorDataPoint<T> {
    String getStationId();
    Instant getTimestamp();
    T getValue();
}